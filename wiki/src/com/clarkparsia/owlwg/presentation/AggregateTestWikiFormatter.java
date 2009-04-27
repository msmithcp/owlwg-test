package com.clarkparsia.owlwg.presentation;

import static com.clarkparsia.owlwg.Constants.TEST_ONTOLOGY_PHYSICAL_URI;
import static java.lang.String.format;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.TestCollection;
import com.clarkparsia.owlwg.testcase.ConsistencyTest;
import com.clarkparsia.owlwg.testcase.InconsistencyTest;
import com.clarkparsia.owlwg.testcase.NegativeEntailmentTest;
import com.clarkparsia.owlwg.testcase.PositiveEntailmentTest;
import com.clarkparsia.owlwg.testcase.Status;
import com.clarkparsia.owlwg.testcase.TestCase;

/**
 * <p>
 * Title: Aggregate Result Wiki Formatter
 * </p>
 * <p>
 * Description: Aggregate test results for presentation in the wg wiki.
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
public class AggregateTestWikiFormatter {

	private final static Logger	log;

	static {
		log = Logger.getLogger( AggregateTestWikiFormatter.class.getCanonicalName() );
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if( args.length != 1 )
			throw new IllegalArgumentException();

		final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		try {
			/*
			 * Load the test ontology from local files before reading the test
			 * cases, otherwise import is likely to fail.
			 */
			manager.loadOntologyFromPhysicalURI( TEST_ONTOLOGY_PHYSICAL_URI );

			OWLOntology casesOntology = manager.loadOntologyFromPhysicalURI( URI.create( args[0] ) );

			TestCollection cases = new TestCollection( casesOntology );

			final int n = cases.size();

			Map<Status, Collection<TestCase>> byStatus = Utilities.indexByStatus( cases );

			StringBuffer out = new StringBuffer();
			out.append( "= Test Case Summary =\n\n" );

			/* General info about report */
			{
				SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mmZ" );
				out.append( format( "Produced: %s\n", df.format( new Date() ) ) );
			}

			/* Summary by status */
			{
				out.append( "\n== By Status ==\n\n" );
				out.append( "{| border=\"1\"\n|-\n!Status!!Count\n" );
				for( Status s : Status.values() )
					out.append( format( "|-\n|%s||%d\n", s, byStatus.get( s ).size() ) );
				out.append( format( "|-\n|NO STATUS||%d\n", byStatus.get( null ).size() ) );
				out.append( format( "|-\n|TOTAL||%d\n", n ) );

				out.append( "|}\n" );
			}

			/* Summary by type and status */
			{
				Status[] statuses = new Status[Status.values().length + 1];
				System.arraycopy( Status.values(), 0, statuses, 0, statuses.length - 1 );
				statuses[statuses.length - 1] = null;

				out.append( "\n== By type and status ==\n\n" );
				out.append( "{| border=\"1\"\n|-\n! " );
				for( Status s : Status.values() )
					out.append( format( "!!%s", s ) );
				out.append( "!!NO STATUS\n" );

				out.append( "|-\n!Consistency\n" );
				for( Status s : statuses ) {
					int i = 0;
					for( TestCase c : byStatus.get( s ) )
						if( c instanceof ConsistencyTest )
							i++;
					out.append( format( "|%d\n", i ) );
				}

				out.append( "|-\n!Inconsistency\n" );
				for( Status s : statuses ) {
					int i = 0;
					for( TestCase c : byStatus.get( s ) )
						if( c instanceof InconsistencyTest )
							i++;
					out.append( format( "|%d\n", i ) );
				}

				out.append( "|-\n!Positive Entailment\n" );
				for( Status s : statuses ) {
					int i = 0;
					for( TestCase c : byStatus.get( s ) )
						if( c instanceof PositiveEntailmentTest )
							i++;
					out.append( format( "|%d\n", i ) );
				}

				out.append( "|-\n!Negative Entailment\n" );
				for( Status s : statuses ) {
					int i = 0;
					for( TestCase c : byStatus.get( s ) )
						if( c instanceof NegativeEntailmentTest )
							i++;
					out.append( format( "|%d\n", i ) );
				}

				out.append( "|}\n" );
			}

			System.out.println( out );

		} catch( OWLOntologyCreationException e ) {
			log.log( Level.SEVERE, "Ontology creation exception caught.", e );
		}
	}
}
