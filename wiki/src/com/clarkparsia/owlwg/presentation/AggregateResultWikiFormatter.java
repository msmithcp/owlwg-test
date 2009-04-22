package com.clarkparsia.owlwg.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.Harness;
import com.clarkparsia.owlwg.TestCollection;
import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testrun.TestRunResult;
import com.clarkparsia.owlwg.testrun.TestRunResultParser;

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
public class AggregateResultWikiFormatter {

	private final static Logger	log;

	static {
		log = Logger.getLogger( AggregateResultWikiFormatter.class.getCanonicalName() );
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if( args.length < 2 )
			throw new IllegalArgumentException();

		final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		try {
			/*
			 * Load the test and results ontology from local files before
			 * reading the test cases, otherwise import of them is likely to
			 * fail.
			 */
			manager.loadOntologyFromPhysicalURI( Harness.TEST_ONTOLOGY_PHYSICAL_URI );
			manager.loadOntologyFromPhysicalURI( Harness.RESULTS_ONTOLOGY_PHYSICAL_URI );

			OWLOntology casesOntology = manager.loadOntologyFromPhysicalURI( URI.create( args[0] ) );
			List<OWLOntology> resultsOntologies = new ArrayList<OWLOntology>();
			for( int i = 1; i < args.length; i++ ) {
				resultsOntologies
						.add( manager.loadOntologyFromPhysicalURI( URI.create( args[i] ) ) );
			}

			TestCollection cases = new TestCollection( casesOntology );
			Map<String, TestCase> caseMap = new HashMap<String, TestCase>();
			for( TestCase c : cases )
				caseMap.put( c.getIdentifier(), c );

			TestRunResultParser parser = new TestRunResultParser();
			for( OWLOntology o : resultsOntologies ) {
				Collection<TestRunResult> results = parser.getResults( o, caseMap );
				System.out.println( String.format( "Results ontology \"%s\" contained %d results",
						o.getURI(), results.size() ) );
			}

		} catch( OWLOntologyCreationException e ) {
			log.log( Level.SEVERE, "Ontology creation exception caught.", e );
		}
	}

}
