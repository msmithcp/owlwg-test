package com.clarkparsia.owlwg.owlapi3.runner.impl;

import java.net.URI;
import java.util.Collections;

import org.semanticweb.owlapi.inference.OWLReasoner;
import org.semanticweb.owlapi.inference.OWLReasonerException;
import org.semanticweb.owlapi.inference.OWLReasonerFactory;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * <p>
 * Title: OWLAPI v3 Reasoner Test Runner
 * </p>
 * <p>
 * Description: Wrapper to use any reasoner implementing the OWLAPI OWLReasoner
 * interface to run reasoning test cases.
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
public class OwlApi3ReasonerTestRunner extends OwlApi3AbstractRunner {

	private final OWLReasonerFactory	reasonerFactory;
	private final URI					uri;

	public OwlApi3ReasonerTestRunner(OWLReasonerFactory reasonerFactory, URI runnerUri) {
		this.reasonerFactory = reasonerFactory;
		this.uri = runnerUri;
	}

	public String getName() {
		return reasonerFactory.getReasonerName();
	}

	public URI getURI() {
		return uri;
	}

	protected boolean isConsistent(OWLOntologyManager manager, OWLOntology o)
			throws OWLReasonerException {
		OWLReasoner reasoner = reasonerFactory.createReasoner( manager, Collections
				.<OWLOntology> emptySet() );
		reasoner.loadOntologies( Collections.singleton( o ) );
		return reasoner.isConsistent( o );
	}

	protected boolean isEntailed(OWLOntologyManager manager, OWLOntology premise,
			OWLOntology conclusion) throws OWLReasonerException {
		OWLReasoner reasoner = reasonerFactory.createReasoner( manager, Collections
				.<OWLOntology> emptySet() );
		OwlApi3EntailmentChecker checker = new OwlApi3EntailmentChecker( reasoner, manager
				.getOWLDataFactory() );

		reasoner.loadOntologies( Collections.singleton( premise ) );
		for( OWLAxiom axiom : conclusion.getLogicalAxioms() ) {
			if( !checker.isEntailed( axiom ) )
				return false;
		}
		return true;
	}

}
