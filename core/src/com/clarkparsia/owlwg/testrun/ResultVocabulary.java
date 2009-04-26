package com.clarkparsia.owlwg.testrun;

import java.net.URI;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLOntologyManager;

/**
 * <p>
 * Title: Result Vocabulary
 * </p>
 * <p>
 * Description: Entities declared in the <a
 * href="http://www.w3.org/2007/OWL/wiki/Test_Result_Format">OWL 2 Test Result
 * Ontology</a>.
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
public class ResultVocabulary {

	public enum AnnotationProperty {
		DETAILS("details");

		private final URI	uri;

		private AnnotationProperty(String localName) {
			uri = URI.create( URI_BASE + localName );
		}

		public URI getAnnotationPropertyURI() {
			return uri;
		}
	}

	public enum Class {
		CONSISTENCY_RUN("ConsistencyRun"), FAILING_RUN("FailingRun"),
		INCOMPLETE_RUN("IncompleteRun"), INCONSISTENCY_RUN("InconsistencyRun"),
		NEGATIVE_ENTAILMENT_RUN("NegativeEntailmentRun"), PASSING_RUN("PassingRun"),
		POSITIVE_ENTAILMENT_RUN("PositiveEntailmentRun"),
		SYNTAX_CONSTRAINT_RUN("SyntaxConstraintRun"),
		SYNTAX_TRANSLATION_RUN("SyntaxTranslationRun"), TEST_RUN("TestRun");

		private final OWLClass	cls;

		private Class(String localName) {
			cls = manager.getOWLDataFactory().getOWLClass( URI.create( URI_BASE + localName ) );
		}

		public OWLClass getOWLClass() {
			return cls;
		}
	}

	public enum ObjectProperty {
		SYNTAX_CONSTRAINT("syntaxConstraint"), RUNNER("runner"), TEST("test");

		private final OWLObjectProperty	op;

		private ObjectProperty(String localName) {
			op = manager.getOWLDataFactory().getOWLObjectProperty(
					URI.create( URI_BASE + localName ) );
		}

		public OWLObjectProperty getOWLObjectProperty() {
			return op;
		}
	}

	private static final OWLOntologyManager	manager;
	public static final URI					ONTOLOGY_URI;

	private static final String				URI_BASE;

	static {
		final String onturi = "http://www.w3.org/2007/OWL/testResultOntology";

		ONTOLOGY_URI = URI.create( onturi );
		URI_BASE = onturi + "#";

		manager = OWLManager.createOWLOntologyManager();
	}
}
