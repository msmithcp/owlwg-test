package com.clarkparsia.owlwg.runner.pellet;

import java.net.URI;
import java.util.logging.Level;

import org.mindswap.pellet.owlapi.PelletReasonerFactory;
import org.mindswap.pellet.owlapi.Reasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.owlapi2.runner.impl.OwlApi2ReasonerTestRunner;
import com.clarkparsia.owlwg.owlapi2.runner.impl.OwlApi2AbstractRunner;

/**
 * <p>
 * Title: Pellet Test Runner
 * </p>
 * <p>
 * Description: Pellet 2.0 based test case runner. More efficient than use of
 * {@link OwlApi2ReasonerTestRunner} with Pellet because it uses the Pellet
 * entailment checker (which is more efficient for some axioms).
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
public class PelletTestRunner extends OwlApi2AbstractRunner {

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
		Reasoner reasoner = reasonerFactory.createReasoner( manager );
		return reasoner.isConsistent( o );
	}

	@Override
	protected boolean isEntailed(OWLOntologyManager manager, OWLOntology premise,
			OWLOntology conclusion) throws OWLReasonerException {
		Reasoner reasoner = reasonerFactory.createReasoner( manager );
		reasoner.loadOntology( premise );
		return reasoner.isEntailed( conclusion );
	}
}
