package com.clarkparsia.owlwg.testcase;

import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;

/**
 * <p>
 * Title: Consistency Test Case
 * </p>
 * <p>
 * Description: See <a
 * href="http://www.w3.org/TR/owl2-test/#Consistency_Tests">OWL 2 Conformance:
 * Consistency Tests</a>.
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
public class ConsistencyTest extends AbstractPremisedTest {

	public ConsistencyTest(OWLOntology ontology, OWLIndividual i) {
		super( ontology, i );
	}

	public void accept(TestCaseVisitor visitor) {
		visitor.visit( this );
	}

	@Override
	public String toString() {
		return String.format( "ConsistencyTest(%s)", getIdentifier() );
	}

}
