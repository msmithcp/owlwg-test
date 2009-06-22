package com.clarkparsia.owlwg;

import static com.clarkparsia.owlwg.Constants.TEST_ONTOLOGY_PHYSICAL_URI;
import static com.clarkparsia.owlwg.Harness.parseFilterCondition;
import static java.lang.String.format;

import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import com.clarkparsia.owlwg.testcase.TestCase;
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
	}

	public static void main(String[] args) {

		Options options = new Options();
		Option o = new Option( "f", "filter", true,
				"Specifies a filter that tests must match to be run" );
		o.setArgName( "FILTER_STACK" );
		options.addOption( o );
		o = new Option( "s", "sort", false, "Sort the results by identifier" );
		options.addOption( o );

		FilterCondition filter;
		HelpFormatter help = new HelpFormatter();
		URI testFileUri;
		boolean sort;
		try {
			CommandLineParser parser = new GnuParser();
			CommandLine line = parser.parse( options, args );

			String filterString = line.getOptionValue( "filter" );
			filter = (filterString == null)
				? FilterCondition.ACCEPT_ALL
				: parseFilterCondition( filterString );

			sort = line.hasOption( "sort" );

			String[] remaining = line.getArgs();
			if( remaining.length != 1 )
				throw new IllegalArgumentException();

			testFileUri = URI.create( remaining[0] );
		} catch( ParseException e ) {
			log.log( Level.SEVERE, "Command line parsing failed.", e );
			help.printHelp( 80, ListCases.class.getCanonicalName(), "", options, "" );
			return;
		} catch( IllegalArgumentException e ) {

			log.log( Level.SEVERE, "Command line parsing failed.", e );
			return;
		}

		final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		try {
			manager.loadOntologyFromPhysicalURI( TEST_ONTOLOGY_PHYSICAL_URI );

			OWLOntology casesOntology = manager.loadOntologyFromPhysicalURI( testFileUri );

			TestCollection cases = new TestCollection( casesOntology, filter );
			List<TestCase> l = cases.asList();
			if( sort ) {
				Collections.sort( l, new Comparator<TestCase>() {
					public int compare(TestCase o1, TestCase o2) {
						return o1.getIdentifier().compareTo( o2.getIdentifier() );
					}
				} );
			}
			Iterator<TestCase> it = l.iterator();
			cases = null;

			int n = l.size();
			System.out.println( format( "%d test cases matched filter:", n ) );
			while( it.hasNext() ) {
				TestCase c = it.next();
				System.out.println( format( "\"%s\" %s", c.getIdentifier(), c.getURI() ) );
				it.remove();
				n++;
			}

		} catch( OWLOntologyCreationException e ) {
			log.log( Level.SEVERE, "Ontology creation exception caught.", e );
		}
	}

}
