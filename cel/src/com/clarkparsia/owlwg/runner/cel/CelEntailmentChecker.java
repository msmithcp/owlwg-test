package com.clarkparsia.owlwg.runner.cel;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLAntiSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLAxiomAnnotationAxiom;
import org.semanticweb.owl.model.OWLAxiomVisitorEx;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owl.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owl.model.OWLDataSubPropertyAxiom;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owl.model.OWLDisjointClassesAxiom;
import org.semanticweb.owl.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owl.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLDisjointUnionAxiom;
import org.semanticweb.owl.model.OWLEntityAnnotationAxiom;
import org.semanticweb.owl.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owl.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owl.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owl.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLImportsDeclaration;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyChainSubPropertyAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owl.model.OWLObjectSubPropertyAxiom;
import org.semanticweb.owl.model.OWLOntologyAnnotationAxiom;
import org.semanticweb.owl.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLSameIndividualsAxiom;
import org.semanticweb.owl.model.OWLSubClassAxiom;
import org.semanticweb.owl.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owl.model.SWRLRule;

import de.tudresden.inf.lat.cel.owlapi.UnsupportedReasonerOperationInCelException;

/**
 * The EntailmentChecker class for the CEL reasoner.
 * 
 * 
 * @author Marcel Lippmann
 * 
 */
public class CelEntailmentChecker {

	private OWLReasoner reasoner;

	private OWLDataFactory factory;

	private CelAxiomVisitor axiomVisitor;

	private OWLReasonerException exception;

	/**
	 * The standard constructor.
	 * 
	 * @param reasoner
	 *            CEL reasoner instance
	 * @param factory
	 *            OWL data factory
	 */
	public CelEntailmentChecker(OWLReasoner reasoner, OWLDataFactory factory) {

		this.reasoner = reasoner;
		this.factory = factory;

		this.axiomVisitor = new CelAxiomVisitor();
	}

	/**
	 * This method checks for entailment.
	 * 
	 * @param axiom
	 *            An OWL axiom
	 * @return whether the axiom is entailed
	 */
	public boolean isEntailed(OWLAxiom axiom) throws OWLReasonerException {

		boolean ret;

		exception = null;

		ret = axiom.accept(axiomVisitor);

		// This is awful code.
		// This kind of exception handling is due to the fact that the
		// OWLAxiomVisitorEx<Boolean> class does not offer visit()-methods
		// which throw an exception.
		// Nonetheless, we somehow have to stick to the interface.
		if (exception != null) {
			throw exception;
		}

		return ret;
	}

	private class CelAxiomVisitor implements OWLAxiomVisitorEx<Boolean> {

		@Override
		public Boolean visit(OWLSubClassAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isSubClassOf(axiom.getSubClass(), axiom
						.getSuperClass());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {

			boolean ret = false;

			OWLDescription hasValue = factory.getOWLObjectValueRestriction(
					axiom.getProperty(), axiom.getObject());
			OWLDescription negHasValue = factory
					.getOWLObjectComplementOf(hasValue);

			try {

				ret = reasoner.hasType(axiom.getSubject(), negHasValue, false);

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isAntiSymmetric(axiom.getProperty()
						.asOWLObjectProperty());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;

		}

		@Override
		public Boolean visit(OWLReflexiveObjectPropertyAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isReflexive(axiom.getProperty()
						.asOWLObjectProperty());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLDisjointClassesAxiom axiom) {

			boolean ret = true;

			int n = axiom.getDescriptions().size();

			OWLDescription[] classes = axiom.getDescriptions().toArray(
					new OWLDescription[n]);

			boolean done = false;

			for (int i = 0; i < n - 1 && !done; ++i) {

				for (int j = i + 1; j < n && !done; ++j) {

					OWLDescription notj = factory
							.getOWLObjectComplementOf(classes[j]);

					try {

						if (!reasoner.isSubClassOf(classes[i], notj)) {
							ret = false;
							done = true;
						}

					} catch (OWLReasonerException e) {

						exception = e;
					}
				}
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLDataPropertyDomainAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: data property domain");

			return false;
		}

		@Override
		public Boolean visit(OWLImportsDeclaration axiom) {

			return true;
		}

		@Override
		public Boolean visit(OWLAxiomAnnotationAxiom axiom) {

			return true;
		}

		@Override
		public Boolean visit(OWLObjectPropertyDomainAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isSubClassOf(factory
						.getOWLObjectSomeRestriction(axiom.getProperty(),
								factory.getOWLThing()), axiom.getDomain());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLEquivalentObjectPropertiesAxiom axiom) {

			// FIXME: really not supported?
			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: equivalent object properties");

			return false;
		}

		@Override
		public Boolean visit(OWLNegativeDataPropertyAssertionAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: data property assertion");

			return false;
		}

		@Override
		public Boolean visit(OWLDifferentIndividualsAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: different individuals");

			return false;
		}

		@Override
		public Boolean visit(OWLDisjointDataPropertiesAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: disjoint data properties");

			return false;
		}

		@Override
		public Boolean visit(OWLDisjointObjectPropertiesAxiom axiom) {

			// FIXME: really not supported?
			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: disjoint object properties");

			return false;
		}

		@Override
		public Boolean visit(OWLObjectPropertyRangeAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isSubClassOf(factory.getOWLThing(), factory
						.getOWLObjectAllRestriction(axiom.getProperty(), axiom
								.getRange()));

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLObjectPropertyAssertionAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.hasObjectPropertyRelationship(
						axiom.getSubject(), axiom.getProperty(), axiom
								.getObject());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLFunctionalObjectPropertyAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isFunctional(axiom.getProperty()
						.asOWLObjectProperty());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLObjectSubPropertyAxiom axiom) {

			// FIXME: really not supported? (should be)
			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: object sub property");

			return false;
		}

		@Override
		public Boolean visit(OWLDisjointUnionAxiom axiom) {

			// FIXME: really not supported? (should be)
			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: disjoint union");

			return false;
		}

		@Override
		public Boolean visit(OWLDeclarationAxiom axiom) {

			return true;
		}

		@Override
		public Boolean visit(OWLEntityAnnotationAxiom axiom) {

			return true;
		}

		@Override
		public Boolean visit(OWLOntologyAnnotationAxiom axiom) {

			return true;
		}

		@Override
		public Boolean visit(OWLSymmetricObjectPropertyAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isSymmetric(axiom.getProperty()
						.asOWLObjectProperty());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLDataPropertyRangeAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: data property range");

			return false;
		}

		@Override
		public Boolean visit(OWLFunctionalDataPropertyAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: functional data property");

			return false;
		}

		@Override
		public Boolean visit(OWLEquivalentDataPropertiesAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: equivalent data properties");

			return false;
		}

		@Override
		public Boolean visit(OWLClassAssertionAxiom axiom) {

			boolean ret = false;

			OWLIndividual ind = axiom.getIndividual();
			OWLDescription c = axiom.getDescription();

			try {

				if (ind.isAnonymous()) {
					ret = reasoner.isSatisfiable(c);
				} else {
					ret = reasoner.hasType(ind, c, false);
				}

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLEquivalentClassesAxiom axiom) {

			boolean ret = true;

			OWLDescription first = null;
			for (OWLDescription description : axiom.getDescriptions()) {

				if (first == null) {
					first = description;
					continue;
				}

				try {

					ret = reasoner.isEquivalentClass(first, description);

				} catch (OWLReasonerException e) {

					exception = e;
					break;
				}

				if (ret == false) {
					break;
				}
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLDataPropertyAssertionAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: data property assertion");

			return false;
		}

		@Override
		public Boolean visit(OWLTransitiveObjectPropertyAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isTransitive(axiom.getProperty()
						.asOWLObjectProperty());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLIrreflexiveObjectPropertyAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isIrreflexive(axiom.getProperty()
						.asOWLObjectProperty());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLDataSubPropertyAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: data sub property");

			return false;
		}

		@Override
		public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {

			boolean ret = false;

			try {

				ret = reasoner.isInverseFunctional(axiom.getProperty()
						.asOWLObjectProperty());

			} catch (OWLReasonerException e) {

				exception = e;
			}

			return ret;
		}

		@Override
		public Boolean visit(OWLSameIndividualsAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: same individuals");

			return false;
		}

		@Override
		public Boolean visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {

			// FIXME: really unsupported?
			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: property chain sub property");

			return false;
		}

		@Override
		public Boolean visit(OWLInverseObjectPropertiesAxiom axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: inverse object property");

			return false;
		}

		@Override
		public Boolean visit(SWRLRule axiom) {

			exception = new UnsupportedReasonerOperationInCelException(
					"Unsupported entailment check: SWRL rule");

			return false;
		}

	}
}
