package com.clarkparsia.owlwg.owlapi2.runner.impl;

import java.net.URI;
import java.util.Collections;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;



/**
 * <p>
 * Title: OWLAPIv2 Reasoner Test Runner
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
public class OwlApi2ReasonerTestRunner extends OwlApi2AbstractRunner {

	private final OWLReasonerFactory	reasonerFactory;
	private final URI					uri;

	public OwlApi2ReasonerTestRunner(OWLReasonerFactory reasonerFactory, URI runnerUri) {
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
		OWLReasoner reasoner = reasonerFactory.createReasoner( manager );
		reasoner.loadOntologies( Collections.singleton( o ) );
		return reasoner.isConsistent( o );
	}

	protected boolean isEntailed(OWLOntologyManager manager, OWLOntology premise,
			OWLOntology conclusion) throws OWLReasonerException {
		OWLReasoner reasoner = reasonerFactory.createReasoner( manager );
		OwlApi2EntailmentChecker checker = new OwlApi2EntailmentChecker( reasoner, manager.getOWLDataFactory() );

		reasoner.loadOntologies( Collections.singleton( premise ) );
		for( OWLAxiom axiom : conclusion.getLogicalAxioms() ) {
			if( !checker.isEntailed( axiom ) )
				return false;
		}
		return true;
	}

}
