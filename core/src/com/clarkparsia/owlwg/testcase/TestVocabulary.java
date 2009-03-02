package com.clarkparsia.owlwg.testcase;

import java.net.URI;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLOntologyManager;

/**
 * <p>
 * Title: Test Vocabulary
 * </p>
 * <p>
 * Description: Entities declared in the <a
 * href="http://www.w3.org/TR/owl2-test/#Complete_Test_Ontology">OWL 2 Test
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
public class TestVocabulary {

	public enum Class {
		CONSISTENCY_TEST("ConsistencyTest"), INCONSISTENCY_TEST("InconsistencyTest"),
		NEGATIVE_ENTAILMENT_TEST("NegativeEntailmentTest"),
		POSITIVE_ENTAILMENT_TEST("PositiveEntailmentTest"), TEST_CASE("TestCase");

		private final OWLClass	cls;

		private Class(String localName) {
			cls = manager.getOWLDataFactory().getOWLClass( URI.create( URI_BASE + localName ) );
		}

		public OWLClass getOWLClass() {
			return cls;
		}
	}

	public enum DatatypeProperty {
		FUNCTIONAL_CONCLUSION_ONTOLOGY("fsConclusionOntology"),
		FUNCTIONAL_NONCONCLUSION_ONTOLOGY("fsNonConclusionOntology"),
		FUNCTIONAL_PREMISE_ONTOLOGY("fsPremiseOntology"), IDENTIFIER("identifier"),
		OWLXML_CONCLUSION_ONTOLOGY("owlXmlConclusionOntology"),
		OWLXML_NONCONCLUSION_ONTOLOGY("owlXmlNonConclusionOntology"),
		OWLXML_PREMISE_ONTOLOGY("owlXmlPremiseOntology"),
		RDFXML_CONCLUSION_ONTOLOGY("rdfXmlConclusionOntology"),
		RDFXML_NONCONCLUSION_ONTOLOGY("rdfXmlNonConclusionOntology"),
		RDFXML_PREMISE_ONTOLOGY("rdfXmlPremiseOntology");

		private final OWLDataProperty	dp;

		private DatatypeProperty(String localName) {
			dp = manager.getOWLDataFactory()
					.getOWLDataProperty( URI.create( URI_BASE + localName ) );
		}

		public OWLDataProperty getOWLDataProperty() {
			return dp;
		}
	}

	public enum Individual {
		APPROVED("Approved"), DL("DL"), EL("EL"), FULL("FULL"), FUNCTIONAL("FUNCTIONAL"),
		OWLXML("OWLXML"), PROPOSED("Proposed"), QL("QL"), RDFXML("RDFXML"), REJECTED("Rejected"),
		RL("RL");

		private final OWLIndividual	i;

		private Individual(String localName) {
			i = manager.getOWLDataFactory().getOWLIndividual( URI.create( URI_BASE + localName ) );
		}

		public OWLIndividual getOWLIndividual() {
			return i;
		}
	}

	public enum ObjectProperty {
		PROFILE("profile"), SEMANTICS("semantics"), SPECIES("species"), STATUS("status");

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

	private static final String				URI_BASE;

	static {
		URI_BASE = "http://www.w3.org/2007/OWL/testOntology#";

		manager = OWLManager.createOWLOntologyManager();
	}
}
