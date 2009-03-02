package com.clarkparsia.owlwg.runner.factpp;

import java.net.URI;
import java.util.Collections;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.reasonerfactory.factpp.FaCTPlusPlusReasonerFactory;

import uk.ac.manchester.cs.factplusplus.InconsistentOntologyException;
import uk.ac.manchester.cs.factplusplus.owlapi.FaCTPlusPlusReasonerException;

import com.clarkparsia.owlwg.runner.AbstractTestRunner;
import com.clarkparsia.owlwg.runner.EntailmentChecker;

/**
 * <p>
 * Title: FaCT++ Test Runner
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
public class FaCTPlusPlusTestRunner extends AbstractTestRunner {

	public URI getURI() {
		return uri;
	}

	@Override
	protected boolean isConsistent(OWLOntologyManager manager, OWLOntology o)
			throws OWLReasonerException {

		OWLReasoner reasoner = reasonerFactory.createReasoner( manager );
		reasoner.loadOntologies( Collections.singleton( o ) );

		try {
			reasoner.classify();
			return reasoner.isConsistent( o );
		} catch( FaCTPlusPlusReasonerException e ) {
			Throwable cause = e.getCause();
			if( (cause != null) && (cause instanceof InconsistentOntologyException) )
				return false;
			else
				throw e;
		}
	}

	@Override
	protected boolean isEntailed(OWLOntologyManager manager, OWLOntology premise,
			OWLOntology conclusion) throws OWLReasonerException {

		OWLReasoner reasoner = reasonerFactory.createReasoner( manager );

		reasoner.loadOntologies( Collections.singleton( premise ) );
		reasoner.classify();

		EntailmentChecker checker = new EntailmentChecker( reasoner, manager.getOWLDataFactory() );
		for( OWLAxiom axiom : conclusion.getLogicalAxioms() ) {
			if( !checker.isEntailed( axiom ) )
				return false;
		}

		return true;
	}

	private static final FaCTPlusPlusReasonerFactory	reasonerFactory;
	private static final URI							uri;

	static {
		reasonerFactory = new FaCTPlusPlusReasonerFactory();
		uri = URI.create( "http://owl.man.ac.uk/factplusplus/" );
	}
}
