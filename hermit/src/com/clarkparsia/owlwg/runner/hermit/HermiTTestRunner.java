package com.clarkparsia.owlwg.runner.hermit;

import java.net.URI;

import org.semanticweb.HermiT.HermitReasoner;
import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.runner.OWLReasonerTestRunner;

/**
 * <p>
 * Title: HermiT Test Runner
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
public class HermiTTestRunner extends OWLReasonerTestRunner {

	private static class ReasonerFactory implements OWLReasonerFactory {

		public OWLReasoner createReasoner(OWLOntologyManager manager) {
			return new HermitReasoner( manager );
		}

		public String getReasonerName() {
			return "HermiT";
		}

	}

	private static final ReasonerFactory	factory;
	private static final URI				uri;

	static {
		factory = new ReasonerFactory();
		uri = URI.create( "http://hermit-reasoner.com/" );
	}

	public HermiTTestRunner() {
		super( factory, uri );
	}

}
