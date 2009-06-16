package com.clarkparsia.owlwg.runner.cel;

import org.semanticweb.owl.model.OWLOntologyManager;

import de.tudresden.inf.lat.cel.owlapi.CelReasoner;

public class CelReasonerManager {

	private static CelReasonerManager instance = null;
	private CelReasoner reasoner = null;

	private CelReasonerManager() {
	}

	public CelReasoner getCelReasoner(OWLOntologyManager manager) {
		if (this.reasoner == null && manager != null) {
			this.reasoner = new CelReasoner(manager);
		}
		return this.reasoner;
	}

	public static CelReasonerManager getInstance() {
		if (instance == null) {
			instance = new CelReasonerManager();
		}
		return instance;
	}
}
