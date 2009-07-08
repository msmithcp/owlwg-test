package com.clarkparsia.owlwg.owlapi2.testcase.impl;

import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.testcase.TestCase;

/**
 * <p>
 * Title: OWLAPIv2 Test Case
 * </p>
 * <p>
 * Description: OWLAPIv2 specialization of test case, which provides access to
 * the {@link OWLOntologyManager} used in parsing.
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
public interface OwlApi2Case extends TestCase<OWLOntology> {
	public OWLOntologyManager getOWLOntologyManager();
}
