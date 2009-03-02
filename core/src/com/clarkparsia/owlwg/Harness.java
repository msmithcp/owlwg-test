package com.clarkparsia.owlwg;

import java.io.File;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.coode.owl.rdf.turtle.TurtleRenderer;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.runner.TestRunner;
import com.clarkparsia.owlwg.runner.pellet.PelletTestRunner;
import com.clarkparsia.owlwg.testcase.TestCase;
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
	public static final URI		RESULTS_ONTOLOGY_PHYSICAL_URI;
	public static final URI		TEST_ONTOLOGY_PHYSICAL_URI;
	public static final String	TEST_RUNNER_CLASS_PROPERTY;

	static {
		log = Logger.getLogger( Harness.class.getCanonicalName() );

		File f = new File( "ontologies/test-ontology.owl" );
		TEST_ONTOLOGY_PHYSICAL_URI = f.toURI();

		f = new File( "ontologies/results-ontology.owl" );
		RESULTS_ONTOLOGY_PHYSICAL_URI = f.toURI();

		TEST_RUNNER_CLASS_PROPERTY = "Harness.TestRunner";
	}

	public static TestRunner getDefaultRunner() {
		return new PelletTestRunner();
	}

	public static TestRunner getTestRunner() {
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
				log.log( Level.SEVERE, String.format(
						"Test runner class (%s) does not implement %s", clsName, TestRunner.class
								.getCanonicalName() ), e );
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

	public static void main(String[] args) {

		if( args.length != 1 )
			throw new IllegalArgumentException();

		final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		TestRunner runner = getTestRunner();
		if( runner == null )
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

			OWLOntology casesOntology = manager.loadOntologyFromPhysicalURI( URI.create( args[0] ) );
			OWLOntology resultOntology = manager.createOntology( noAxioms );

			TestCollection cases = new TestCollection( casesOntology );
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
				for( TestRunResult result : runner.run( c, 60000 ) ) {
					OWLIndividual i = manager.getOWLDataFactory().getOWLAnonymousIndividual(
							URI.create( "run" + (bnodeid++) ) );

					manager.addAxioms( resultOntology, new HashSet<OWLAxiom>( adapter.asOWLAxioms(
							result, i ) ) );
				}
				it.remove();
			}

			TurtleRenderer renderer = new TurtleRenderer( resultOntology, manager,
					new OutputStreamWriter( System.out ) );

			renderer.render();

		} catch( OWLOntologyCreationException e ) {
			log.log( Level.SEVERE, "Ontology creation exception caught.", e );
		} catch( OWLOntologyChangeException e ) {
			log.log( Level.SEVERE, "Ontology change exception caught.", e );
		}
	}
}
