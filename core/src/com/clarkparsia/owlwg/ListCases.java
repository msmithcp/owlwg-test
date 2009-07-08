package com.clarkparsia.owlwg;

import static com.clarkparsia.owlwg.Constants.TEST_ONTOLOGY_PHYSICAL_URI;
import static com.clarkparsia.owlwg.cli.FilterConditionParser.parse;
import static java.lang.String.format;

import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplateGroupLoader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.cli.FilterConditionParser;
import com.clarkparsia.owlwg.owlapi2.testcase.impl.OwlApi2TestCaseFactory;
import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testcase.TestCaseFactory;
import com.clarkparsia.owlwg.testcase.filter.FilterCondition;

/**
 * <p>
 * Title: List Cases
 * </p>
 * <p>
 * Description: Command line application list test cases found in a file
 * (optionally limiting to those which match a provided filter condition).
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
public class ListCases {

	public static final Logger	log;

	static {
		log = Logger.getLogger( ListCases.class.getCanonicalName() );

		StringTemplateGroupLoader loader = new CommonGroupLoader(
				"com/clarkparsia/owlwg/templates", new StringTemplateErrorListener() {

					public void error(String msg, Throwable e) {
						log.log( Level.SEVERE, msg, e );
					}

					public void warning(String msg) {
						log.warning( msg );
					}

				} );
		StringTemplateGroup.registerGroupLoader( loader );
	}

	public static void main(String[] args) {

		Options options = new Options();
		Option o = new Option( "f", "filter", true,
				"Specifies a filter that tests must match to be run" );
		o.setArgName( "FILTER_STACK" );
		options.addOption( o );

		o = new Option( "s", "sort", false, "Sort the results by identifier" );
		options.addOption( o );

		o = new Option( "F", "output-format", true,
				"Indicate desired output format (default, wiki)" );
		o.setArgName( "OUTPUT_FORMAT" );
		options.addOption( o );

		FilterCondition filter;
		HelpFormatter help = new HelpFormatter();
		URI testFileUri;
		String outputTemplate;
		boolean sort;
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine line = parser.parse( options, args );

			String filterString = line.getOptionValue( "filter" );
			filter = (filterString == null)
				? FilterCondition.ACCEPT_ALL
				: FilterConditionParser.parse( filterString );

			sort = line.hasOption( "sort" );

			String[] remaining = line.getArgs();
			if( remaining.length != 1 )
				throw new IllegalArgumentException();

			String outputFormat = line.getOptionValue( "output-format", "default" );
			if( "default".equalsIgnoreCase( outputFormat ) )
				outputTemplate = "cli-default";
			else if( "wiki".equalsIgnoreCase( outputFormat ) )
				outputTemplate = "cli-wiki";
			else
				throw new IllegalArgumentException( format(
						"Unrecognized output format (%s). Expecting 'default' or 'wiki')",
						outputFormat ) );

			testFileUri = URI.create( remaining[0] );
		} catch( ParseException e ) {
			log.log( Level.SEVERE, "Command line parsing failed.", e );
			help.printHelp( 80, ListCases.class.getCanonicalName(), "", options, "" );
			return;
		} catch( IllegalArgumentException e ) {

			log.log( Level.SEVERE, "Command line parsing failed.", e );
			return;
		}

		final StringTemplateGroup stg = StringTemplateGroup.loadGroup( outputTemplate );
		final StringTemplate template = stg.getInstanceOf( "list-cases" );

		final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		try {
			manager.loadOntologyFromPhysicalURI( TEST_ONTOLOGY_PHYSICAL_URI );

			OWLOntology casesOntology = manager.loadOntologyFromPhysicalURI( testFileUri );

			TestCaseFactory<OWLOntology> factory = new OwlApi2TestCaseFactory();
			TestCollection<OWLOntology> cases = new TestCollection<OWLOntology>( factory,
					casesOntology, filter );
			List<TestCase<OWLOntology>> l = cases.asList();
			if( sort ) {
				Collections.sort( l, new Comparator<TestCase<OWLOntology>>() {
					public int compare(TestCase<OWLOntology> o1, TestCase<OWLOntology> o2) {
						return o1.getIdentifier().compareTo( o2.getIdentifier() );
					}
				} );
			}
			cases = null;

			int n = l.size();
			template.setAttribute( "count", n );
			template.setAttribute( "cases", l );
			System.out.print( template.toString() );

		} catch( OWLOntologyCreationException e ) {
			log.log( Level.SEVERE, "Ontology creation exception caught.", e );
		}
	}

}
