package com.clarkparsia.owlwg.owlapi2.testcase.impl;

import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;

import com.clarkparsia.owlwg.testcase.TestCaseFactory;

/**
 * <p>
 * Title: OWLAPIv2 Test Case Factory
 * </p>
 * <p>
 * Description:
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
public class OwlApi2TestCaseFactory implements TestCaseFactory<OWLOntology> {

	public OwlApi2ConTstImpl getConsistencyTestCase(OWLOntology o, OWLIndividual i) {
		return new OwlApi2ConTstImpl( o, i );
	}

	public OwlApi2IncTstImpl getInconsistencyTestCase(OWLOntology o, OWLIndividual i) {
		return new OwlApi2IncTstImpl( o, i );
	}

	public OwlApi2NegTstImpl getNegativeEntailmentTestCase(OWLOntology o, OWLIndividual i) {
		return new OwlApi2NegTstImpl( o, i );
	}

	public OwlApi2PosTstImpl getPositiveEntailmentTestCase(OWLOntology o, OWLIndividual i) {
		return new OwlApi2PosTstImpl( o, i );
	}

}
