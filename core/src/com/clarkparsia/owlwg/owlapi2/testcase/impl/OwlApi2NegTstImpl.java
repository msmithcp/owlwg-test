package com.clarkparsia.owlwg.owlapi2.testcase.impl;

import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;

import com.clarkparsia.owlwg.testcase.NegativeEntailmentTest;
import com.clarkparsia.owlwg.testcase.TestCaseVisitor;

/**
 * <p>
 * Title: OWLAPIv2 Negative Entailment Test Case
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
public class OwlApi2NegTstImpl extends OwlApi2ETImpl implements NegativeEntailmentTest<OWLOntology> {

	public OwlApi2NegTstImpl(OWLOntology ontology, OWLIndividual i) {
		super( ontology, i, false );
	}

	public void accept(TestCaseVisitor<OWLOntology> visitor) {
		visitor.visit( this );
	}

	@Override
	public String toString() {
		return String.format( "NegativeEntailmentTest(%s)", getIdentifier() );
	}
}
