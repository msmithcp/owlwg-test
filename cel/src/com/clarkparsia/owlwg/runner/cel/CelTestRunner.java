package com.clarkparsia.owlwg.runner.cel;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLLogicalAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.runner.AbstractTestRunner;
import com.clarkparsia.owlwg.runner.EntailmentChecker;
import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testrun.TestRunResult;

import de.tudresden.inf.lat.cel.owlapi.CelReasoner;

/**
 * Test runner for CEL.
 */
public class CelTestRunner extends AbstractTestRunner {

	private static final URI uri;

	static {
		uri = URI.create("http://lat.inf.tu-dresden.de/systems/cel/");
	}

	private long timeout = 0;
	private OWLReasoner reasoner = null;

	public URI getURI() {
		return uri;
	}

	protected void stopReasoner() throws OWLReasonerException {
		this.reasoner.dispose();
	}

	protected boolean isConsistent(OWLOntologyManager manager, OWLOntology o)
			throws OWLReasonerException {
		OWLReasoner reasoner = new CelReasoner(manager);
		this.reasoner = reasoner; // FIXME

		reasoner.loadOntologies(Collections.singleton(o));
		reasoner.classify();
		boolean ret = reasoner.isConsistent(o);
		return ret;
	}

	protected boolean isEntailed(OWLOntologyManager manager,
			OWLOntology premise, OWLOntology conclusion)
			throws OWLReasonerException {

		OWLReasoner reasoner = new CelReasoner(manager);
		this.reasoner = reasoner; // FIXME

		reasoner.loadOntologies(Collections.singleton(premise));
		reasoner.classify();

		EntailmentChecker checker = new EntailmentChecker(reasoner, manager
				.getOWLDataFactory());

		boolean ret = true;
		for (Iterator<OWLLogicalAxiom> it = conclusion.getLogicalAxioms().iterator();ret && it.hasNext();) {
			OWLLogicalAxiom axiom = it.next();

			if (!checker.isEntailed(axiom)) {
				ret = false;
			}
		}
		return ret;
	}

	@Override
	protected TestRunResult run(TestAsRunnable runnable) {
		Thread t = new Thread(runnable);
		t.start();
		try {
			t.join(timeout);
			stopReasoner();
		} catch (InterruptedException e) {
			return runnable.getErrorResult(e);
		} catch (OWLReasonerException e) {
			return runnable.getErrorResult(e);
		}
		if (t.isAlive()) {
			try {
				t.stop();
				return runnable.getTimeoutResult();
			} catch (OutOfMemoryError oome) {
				System.gc();
				return runnable.getTimeoutResult();
			}
		} else {
			try {
				return runnable.getResult();
			} catch (Throwable th) {
				return runnable.getErrorResult(th);
			}
		}
	}

	@Override
	public Collection<TestRunResult> run(TestCase testcase, long timeout) {
		this.timeout = timeout;
		return super.run(testcase, timeout);
	}
}
