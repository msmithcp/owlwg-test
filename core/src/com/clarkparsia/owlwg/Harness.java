package com.clarkparsia.owlwg;

import static com.clarkparsia.owlwg.Constants.RESULTS_ONTOLOGY_PHYSICAL_URI;
import static com.clarkparsia.owlwg.Constants.TEST_ONTOLOGY_PHYSICAL_URI;
import static java.lang.String.format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.coode.owl.rdf.turtle.TurtleRenderer;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.cli.FilterConditionParser;
import com.clarkparsia.owlwg.owlapi2.testcase.impl.OwlApi2TestCaseFactory;
import com.clarkparsia.owlwg.runner.TestRunner;
import com.clarkparsia.owlwg.runner.pellet.PelletTestRunner;
import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testcase.TestCaseFactory;
import com.clarkparsia.owlwg.testcase.filter.FilterCondition;
import com.clarkparsia.owlwg.testrun.ResultVocabulary;
import com.clarkparsia.owlwg.testrun.TestRunResult;
import com.clarkparsia.owlwg.testrun.TestRunResultAdapter;

/**
 * <p>
 * Title: Harness
 * </p>
 * <p>
 * Description: Command line application to run all test cases found in a file
 * (provided as the only command line argument) with a specific test runner.
 * </p>
 * <p>
 * Copyright: Copyright &copy; 2009
 * </p>
 * <p>
 * Company: Clark & Parsia, LLC. <a
 * href="http://clarkparsia.com/"/>http://clarkparsia.com/</a>
 * </p>
 * 
 * @author Mike Smith &lt;msmith@clarkparsia.com&gt;
 */
public class Harness {

	public static final Logger	log;
	public static final String	TEST_RUNNER_CLASS_PROPERTY;
	public static final String	TEST_FACTORY_CLASS_PROPERTY;

	static {
		log = Logger.getLogger( Harness.class.getCanonicalName() );

		TEST_RUNNER_CLASS_PROPERTY = "Harness.TestRunner";
		TEST_FACTORY_CLASS_PROPERTY = "Harness.TestFactory";
	}

	public static TestRunner<OWLOntology> getDefaultRunner() {
		return new PelletTestRunner();
	}

	public static TestCaseFactory<OWLOntology> getDefaultFactory() {
		return new OwlApi2TestCaseFactory();
	}

	public static TestRunner<?> getTestRunner() {
		Class<? extends TestRunner> runner;
		String clsName = System.getProperty( TEST_RUNNER_CLASS_PROPERTY );
		if( clsName != null ) {
			try {
				Class<?> cls = Class.forName( clsName );
				runner = cls.asSubclass( TestRunner.class );
				return runner.newInstance();
			} catch( ClassNotFoundException e ) {
				log.log( Level.SEVERE, "Test runner class not found: " + clsName, e );
				return null;
			} catch( ClassCastException e ) {
				log.log( Level.SEVERE, format( "Test runner class (%s) does not implement %s",
						clsName, TestRunner.class.getCanonicalName() ), e );
				return null;
			} catch( InstantiationException e ) {
				log.log( Level.SEVERE, "Instantiation failed for test runner class: " + clsName, e );
				return null;
			} catch( IllegalAccessException e ) {
				log
						.log( Level.SEVERE, "Illegal access failed for test runner class: "
								+ clsName, e );
				return null;
			}
		}
		else {
			return getDefaultRunner();
		}
	}

	public static TestCaseFactory<?> getTestCaseFactory() {
		Class<? extends TestCaseFactory> factory;
		String clsName = System.getProperty( TEST_FACTORY_CLASS_PROPERTY );
		if( clsName != null ) {
			try {
				Class<?> cls = Class.forName( clsName );
				factory = cls.asSubclass( TestCaseFactory.class );
				return factory.newInstance();
			} catch( ClassNotFoundException e ) {
				log.log( Level.SEVERE, "Test runner class not found: " + clsName, e );
				return null;
			} catch( ClassCastException e ) {
				log.log( Level.SEVERE, format( "Test runner class (%s) does not implement %s",
						clsName, TestRunner.class.getCanonicalName() ), e );
				return null;
			} catch( InstantiationException e ) {
				log.log( Level.SEVERE, "Instantiation failed for test runner class: " + clsName, e );
				return null;
			} catch( IllegalAccessException e ) {
				log
						.log( Level.SEVERE, "Illegal access failed for test runner class: "
								+ clsName, e );
				return null;
			}
		}
		else {
			return getDefaultFactory();
		}
	}

	public static void main(String[] args) {

		Options options = new Options();
		Option o = new Option( "f", "filter", true,
				"Specifies a filter that tests must match to be run" );
		o.setArgName( "FILTER_STACK" );
		options.addOption( o );
		o = new Option( "o", "output", true,
				"Output file for test results (in TURTLE format).  Defaults to stdout." );
		o.setArgName( "OUTPUT_FILE" );
		options.addOption( o );
		o = new Option( "t", "timeout", true, "Per test timeout (in seconds).  Defaults to 60." );
		o.setArgName( "TIMEOUT" );
		options.addOption( o );

		FilterCondition filter;
		HelpFormatter help = new HelpFormatter();
		URI testFileUri;
		Writer resultsWriter;
		long timeoutS, timeout;
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine line = parser.parse( options, args );

			String filterString = line.getOptionValue( "filter" );
			filter = (filterString == null)
				? FilterCondition.ACCEPT_ALL
				: FilterConditionParser.parse( filterString );

			String outFilename = line.getOptionValue( "output" );
			resultsWriter = (outFilename == null)
				? new OutputStreamWriter( System.out )
				: new OutputStreamWriter( new FileOutputStream( new File( outFilename ) ) );

			String timeoutString = line.getOptionValue( "timeout" );
			timeoutS = (timeoutString == null)
				? 60
				: Long.parseLong( timeoutString );
			if( timeoutS < 1 )
				throw new IllegalArgumentException();

			String[] remaining = line.getArgs();
			if( remaining.length != 1 )
				throw new IllegalArgumentException();

			testFileUri = URI.create( remaining[0] );
		} catch( ParseException e ) {
			log.log( Level.SEVERE, "Command line parsing failed.", e );
			help.printHelp( 80, Harness.class.getCanonicalName(), "", options, "" );
			return;
		} catch( FileNotFoundException e ) {
			log.log( Level.SEVERE, "File not found.", e );
			return;
		} catch( IllegalArgumentException e ) {

			log.log( Level.SEVERE, "Command line parsing failed.", e );
			return;
		}
		timeout = timeoutS * 1000;

		final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		TestRunner<?> runner = getTestRunner();
		if( runner == null )
			return;

		TestCaseFactory<?> factory = getTestCaseFactory();
		if( factory == null )
			return;

		try {
			final Set<OWLAxiom> noAxioms = Collections.emptySet();

			/*
			 * Load the test and results ontology from local files before
			 * reading the test cases, otherwise import of them is likely to
			 * fail.
			 */
			manager.loadOntologyFromPhysicalURI( TEST_ONTOLOGY_PHYSICAL_URI );
			manager.loadOntologyFromPhysicalURI( RESULTS_ONTOLOGY_PHYSICAL_URI );

			OWLOntology casesOntology = manager.loadOntologyFromPhysicalURI( testFileUri );
			OWLOntology resultOntology = manager.createOntology( noAxioms );

			TestCollection cases = new TestCollection( factory, casesOntology, filter );
			Iterator<TestCase> it = cases.asList().iterator();
			cases = null;

			manager.removeOntology( casesOntology.getURI() );

			manager
					.addAxiom( resultOntology, manager.getOWLDataFactory()
							.getOWLImportsDeclarationAxiom( resultOntology,
									ResultVocabulary.ONTOLOGY_URI ) );

			TestRunResultAdapter adapter = new TestRunResultAdapter( manager.getOWLDataFactory() );
			int bnodeid = 0;

			while( it.hasNext() ) {
				TestCase c = it.next();
				for( TestRunResult result : runner.run( c, timeout ) ) {
					OWLIndividual i = manager.getOWLDataFactory().getOWLAnonymousIndividual(
							URI.create( "run" + (bnodeid++) ) );

					manager.addAxioms( resultOntology, new HashSet<OWLAxiom>( adapter.asOWLAxioms(
							result, i ) ) );
				}
				it.remove();
			}

			runner.dispose();

			TurtleRenderer renderer = new TurtleRenderer( resultOntology, manager, resultsWriter );

			renderer.render();

		} catch( OWLReasonerException e ) {
			log.log( Level.SEVERE, "Ontology creation exception caught.", e );
		} catch( OWLOntologyCreationException e ) {
			log.log( Level.SEVERE, "Ontology creation exception caught.", e );
		} catch( OWLOntologyChangeException e ) {
			log.log( Level.SEVERE, "Ontology change exception caught.", e );
		}
	}
}
