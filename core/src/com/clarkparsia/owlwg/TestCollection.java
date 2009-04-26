package com.clarkparsia.owlwg;

import static com.clarkparsia.owlwg.testcase.TestVocabulary.Class.CONSISTENCY_TEST;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.Class.INCONSISTENCY_TEST;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.Class.NEGATIVE_ENTAILMENT_TEST;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.Class.POSITIVE_ENTAILMENT_TEST;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;

import com.clarkparsia.owlwg.testcase.ConsistencyTest;
import com.clarkparsia.owlwg.testcase.InconsistencyTest;
import com.clarkparsia.owlwg.testcase.NegativeEntailmentTest;
import com.clarkparsia.owlwg.testcase.PositiveEntailmentTest;
import com.clarkparsia.owlwg.testcase.TestCase;

/**
 * <p>
 * Title: Test Collection
 * </p>
 * <p>
 * Description: Converts an ontology containing test case descriptions into an
 * iterable collection of {@link TestCase} objects
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
public class TestCollection implements Iterable<TestCase> {

	private final Map<OWLIndividual, TestCase>	cases;

	public TestCollection(OWLOntology o) {

		cases = new HashMap<OWLIndividual, TestCase>();
		Set<OWLClassAssertionAxiom> axioms;

		axioms = o.getClassAssertionAxioms( POSITIVE_ENTAILMENT_TEST.getOWLClass() );
		if( axioms != null ) {
			for( OWLClassAssertionAxiom ax : axioms ) {
				final OWLIndividual i = ax.getIndividual();
				cases.put( i, new PositiveEntailmentTest( o, i ) );
			}
		}

		axioms = o.getClassAssertionAxioms( NEGATIVE_ENTAILMENT_TEST.getOWLClass() );
		if( axioms != null ) {
			for( OWLClassAssertionAxiom ax : axioms ) {
				final OWLIndividual i = ax.getIndividual();
				cases.put( i, new NegativeEntailmentTest( o, i ) );
			}
		}

		axioms = o.getClassAssertionAxioms( CONSISTENCY_TEST.getOWLClass() );
		if( axioms != null ) {
			for( OWLClassAssertionAxiom ax : axioms ) {
				final OWLIndividual i = ax.getIndividual();
				/*
				 * Verify the identifier is not already in the map because both
				 * entailment tests may also be marked as consistency tests.
				 */
				if( cases.containsKey( i ) )
					continue;
				final ConsistencyTest t = new ConsistencyTest( o, i );
				if( filter.accepts( t ) && !cases.containsKey( i ) )
					cases.put( i, t );
			}
		}

		axioms = o.getClassAssertionAxioms( INCONSISTENCY_TEST.getOWLClass() );
		if( axioms != null ) {
			for( OWLClassAssertionAxiom ax : axioms ) {
				final OWLIndividual i = ax.getIndividual();
				cases.put( i, new InconsistencyTest( o, i ) );
			}
		}
	}

	public LinkedList<TestCase> asList() {
		return new LinkedList<TestCase>( getTests() );
	}

	private Collection<TestCase> getTests() {
		return Collections.unmodifiableCollection( cases.values() );
	}

	public Iterator<TestCase> iterator() {
		return getTests().iterator();
	}

	public int size() {
		return getTests().size();
	}
}
