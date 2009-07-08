package com.clarkparsia.owlwg.owlapi2.runner.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.inference.UnsupportedReasonerOperationException;
import org.semanticweb.owl.model.OWLAntiSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLAxiomAnnotationAxiom;
import org.semanticweb.owl.model.OWLAxiomVisitor;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataProperty;
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
import org.semanticweb.owl.model.OWLObjectProperty;
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

/**
 * <p>
 * Title: OWLAPI v2 Entailment Checker
 * </p>
 * <p>
 * Description: Incomplete entailment checking implementation for OWLAPI
 * OWLReasoner implementations. Based on the EntailmentChecker distributed with
 * <a href="http://clarkparsia.com/pellet">Pellet</a>, but generalized to avoid
 * Pellet specific methods.
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
public class OwlApi2EntailmentChecker implements OWLAxiomVisitor {
	public static Logger			log			= Logger.getLogger( OwlApi2EntailmentChecker.class
														.getName() );

	private OWLReasonerException	exception	= null;
	private OWLDataFactory			factory;
	private boolean					isEntailed	= false;
	private OWLReasoner				reasoner;

	public OwlApi2EntailmentChecker(OWLReasoner reasoner, OWLDataFactory factory) {
		this.reasoner = reasoner;
		this.factory = factory;
	}

	public boolean isEntailed(OWLAxiom axiom) throws OWLReasonerException {
		isEntailed = false;
		exception = null;

		axiom.accept( this );

		if( exception != null )
			throw exception;

		return isEntailed;
	}

	public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isAntiSymmetric( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLAxiomAnnotationAxiom axiom) {
		isEntailed = true;
		if( log.isLoggable( Level.FINE ) )
			log.fine( "Ignoring axiom annotation " + axiom );
	}

	public void visit(OWLClassAssertionAxiom axiom) {
		OWLIndividual ind = axiom.getIndividual();
		OWLDescription c = axiom.getDescription();

		try {
			if( ind.isAnonymous() )
				isEntailed = reasoner.isSatisfiable( c );
			else
				isEntailed = reasoner.hasType( ind, c, false );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		try {
			isEntailed = reasoner.hasDataPropertyRelationship( axiom.getSubject(), axiom
					.getProperty(), axiom.getObject() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}

	}

	public void visit(OWLDataPropertyDomainAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLDataSomeRestriction( axiom
					.getProperty(), factory.getTopDataType() ), axiom.getDomain() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLDataPropertyRangeAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLThing(), factory
					.getOWLDataAllRestriction( axiom.getProperty(), axiom.getRange() ) );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLDataSubPropertyAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: data sub properties" );
	}

	public void visit(OWLDeclarationAxiom axiom) {
		isEntailed = true;
		if( log.isLoggable( Level.FINE ) )
			log.fine( "Ignoring declaration " + axiom );
	}

	public void visit(OWLDifferentIndividualsAxiom axiom) {
		isEntailed = true;

		ArrayList<OWLIndividual> list = new ArrayList<OWLIndividual>( axiom.getIndividuals() );
		try {
			for( int i = 0; i < list.size() - 1; i++ ) {

				OWLIndividual head = list.get( i );
				for( int j = i + 1; j < list.size(); j++ ) {
					OWLIndividual next = list.get( j );

					if( !reasoner.hasType( head, factory.getOWLObjectComplementOf( factory
							.getOWLObjectOneOf( next ) ), false ) ) {
						isEntailed = false;
						return;
					}
				}
			}
		} catch( OWLReasonerException e ) {
			exception = e;
		}

	}

	public void visit(OWLDisjointClassesAxiom axiom) {
		isEntailed = true;

		int n = axiom.getDescriptions().size();
		OWLDescription[] classes = axiom.getDescriptions().toArray( new OWLDescription[n] );
		try {
			for( int i = 0; i < n - 1; i++ ) {
				for( int j = i + 1; j < n; j++ ) {
					OWLDescription notj = factory.getOWLObjectComplementOf( classes[j] );
					if( !reasoner.isSubClassOf( classes[i], notj ) ) {
						isEntailed = false;
						return;
					}
				}
			}
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: disjoint data properties" );
	}

	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: disjoint object properties" );
	}

	public void visit(OWLDisjointUnionAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: disjoint union" );
	}

	public void visit(OWLEntityAnnotationAxiom axiom) {
		isEntailed = true;
		if( log.isLoggable( Level.FINE ) )
			log.fine( "Ignoring entity annotation " + axiom );
	}

	public void visit(OWLEquivalentClassesAxiom axiom) {
		isEntailed = true;

		try {
			Iterator<OWLDescription> i = axiom.getDescriptions().iterator();
			if( i.hasNext() ) {
				OWLDescription first = i.next();

				while( i.hasNext() && isEntailed ) {
					OWLDescription next = i.next();

					isEntailed = reasoner.isEquivalentClass( first, next );
				}
			}
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: equivalent data properties" );
	}

	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: equivalent object properties" );
	}

	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isFunctional( (OWLDataProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isFunctional( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLImportsDeclaration axiom) {
		isEntailed = true;
		if( log.isLoggable( Level.FINE ) )
			log.fine( "Ignoring imports declaration " + axiom );
	}

	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isInverseFunctional( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: inverse object properties" );
	}

	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isIrreflexive( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		OWLDescription hasValue = factory.getOWLDataValueRestriction( axiom.getProperty(), axiom
				.getObject() );
		OWLDescription doesNotHaveValue = factory.getOWLObjectComplementOf( hasValue );
		try {
			isEntailed = reasoner.hasType( axiom.getSubject(), doesNotHaveValue, false );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		OWLDescription hasValue = factory.getOWLObjectValueRestriction( axiom.getProperty(), axiom
				.getObject() );
		OWLDescription doesNotHaveValue = factory.getOWLObjectComplementOf( hasValue );
		try {
			isEntailed = reasoner.hasType( axiom.getSubject(), doesNotHaveValue, false );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		try {
			isEntailed = reasoner.hasObjectPropertyRelationship( axiom.getSubject(), axiom
					.getProperty(), axiom.getObject() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
		throw new UnsupportedOperationException();
	}

	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLObjectSomeRestriction( axiom
					.getProperty(), factory.getOWLThing() ), axiom.getDomain() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLThing(), factory
					.getOWLObjectAllRestriction( axiom.getProperty(), axiom.getRange() ) );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLObjectSubPropertyAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: object sub properties" );
	}

	public void visit(OWLOntologyAnnotationAxiom axiom) {
		isEntailed = true;
		if( log.isLoggable( Level.FINE ) )
			log.fine( "Ignoring ontology annotation " + axiom );
	}

	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isReflexive( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLSameIndividualsAxiom axiom) {
		isEntailed = true;

		try {
			Iterator<OWLIndividual> i = axiom.getIndividuals().iterator();
			if( i.hasNext() ) {
				OWLIndividual first = i.next();

				while( i.hasNext() ) {
					OWLIndividual next = i.next();

					if( !reasoner.hasType( first, factory.getOWLObjectOneOf( next ), false ) ) {
						isEntailed = false;
						return;
					}
				}
			}
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLSubClassAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( axiom.getSubClass(), axiom.getSuperClass() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isSymmetric( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isTransitive( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(SWRLRule rule) {
		throw new UnsupportedOperationException();
	}

}
