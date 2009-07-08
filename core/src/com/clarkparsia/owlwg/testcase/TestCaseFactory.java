package com.clarkparsia.owlwg.testcase;

import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;

public interface TestCaseFactory<O> {

	public ConsistencyTest<O> getConsistencyTestCase(OWLOntology o, OWLIndividual i);

	public InconsistencyTest<O> getInconsistencyTestCase(OWLOntology o, OWLIndividual i);

	public PositiveEntailmentTest<O> getPositiveEntailmentTestCase(OWLOntology o, OWLIndividual i);

	public NegativeEntailmentTest<O> getNegativeEntailmentTestCase(OWLOntology o, OWLIndividual i);
}
