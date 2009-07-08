package com.clarkparsia.owlwg.runner.pellet;

import java.net.URI;
import java.util.Collections;
import java.util.logging.Level;

import org.semanticweb.owlapi.inference.OWLReasonerException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.owlwg.owlapi3.runner.impl.OwlApi3AbstractRunner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.clarkparsia.pellet.owlapiv3.Reasoner;

/**
 * <p>
 * Title: Pellet OWLAPIv3 Test Runner
 * </p>
 * <p>
 * Description: Pellet 2.0 based test case runner using alpha OWLAPIv3 support.
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
public class PelletOA3TestRunner extends OwlApi3AbstractRunner {

	private static final PelletReasonerFactory	reasonerFactory;

	private static final URI					uri;

	static {
		uri = URI.create( "http://clarkparsia.com/pellet" );
		reasonerFactory = new PelletReasonerFactory();
		org.mindswap.pellet.KnowledgeBase.log.setLevel( Level.SEVERE );
		org.mindswap.pellet.owlapi.Reasoner.log.setLevel( Level.SEVERE );
	}

	public String getName() {
		return "Pellet";
	}

	public URI getURI() {
		return uri;
	}

	@Override
	protected boolean isConsistent(OWLOntologyManager manager, OWLOntology o)
			throws OWLReasonerException {
		Reasoner reasoner = reasonerFactory.createReasoner( manager, Collections
				.<OWLOntology> emptySet() );
		return reasoner.isConsistent( o );
	}

	@Override
	protected boolean isEntailed(OWLOntologyManager manager, OWLOntology premise,
			OWLOntology conclusion) throws OWLReasonerException {
		Reasoner reasoner = reasonerFactory.createReasoner( manager, Collections
				.<OWLOntology> emptySet() );
		reasoner.loadOntology( premise );
		return reasoner.isEntailed( conclusion );
	}
}
