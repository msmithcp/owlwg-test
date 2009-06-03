package com.clarkparsia.owlwg.runner.cel;

import java.net.URI;
import java.util.Collections;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.runner.AbstractTestRunner;
import com.clarkparsia.owlwg.runner.EntailmentChecker;

import de.tudresden.inf.lat.cel.owlapi.CelReasoner;

/**
 * Test runner for CEL.
 */
public class CelTestRunner extends AbstractTestRunner {

	private static final URI uri;

	static {
		uri = URI.create("http://lat.inf.tu-dresden.de/systems/cel/");
	}

	public URI getURI() {
		return uri;
	}

	protected boolean isConsistent(OWLOntologyManager manager, OWLOntology o)
			throws OWLReasonerException {

		OWLReasoner reasoner = new CelReasoner(manager);

		reasoner.loadOntologies(Collections.singleton(o));
		reasoner.classify();

		return reasoner.isConsistent(o);
	}

	protected boolean isEntailed(OWLOntologyManager manager,
			OWLOntology premise, OWLOntology conclusion)
			throws OWLReasonerException {

		OWLReasoner reasoner = new CelReasoner(manager);

		reasoner.loadOntologies(Collections.singleton(premise));
		reasoner.classify();

		EntailmentChecker checker = new EntailmentChecker(reasoner, manager
				.getOWLDataFactory());

		for (OWLAxiom axiom : conclusion.getLogicalAxioms()) {

			if (!checker.isEntailed(axiom)) {
				return false;
			}
		}

		return true;
	}
}
