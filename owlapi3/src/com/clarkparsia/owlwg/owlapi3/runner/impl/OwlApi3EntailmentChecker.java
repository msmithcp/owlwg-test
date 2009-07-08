package com.clarkparsia.owlwg.owlapi3.runner.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.inference.OWLReasoner;
import org.semanticweb.owlapi.inference.OWLReasonerException;
import org.semanticweb.owlapi.inference.UnsupportedReasonerOperationException;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * <p>
 * Title: OWLAPI v3 Entailment Checker
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
public class OwlApi3EntailmentChecker implements OWLAxiomVisitor {
	public static Logger			log			= Logger.getLogger( OwlApi3EntailmentChecker.class
														.getName() );

	private OWLReasonerException	exception	= null;
	private OWLDataFactory			factory;
	private boolean					isEntailed	= false;
	private OWLReasoner				reasoner;

	public OwlApi3EntailmentChecker(OWLReasoner reasoner, OWLDataFactory factory) {
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

	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isAntiSymmetric( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLClassAssertionAxiom axiom) {
		OWLIndividual ind = axiom.getIndividual();
		OWLClassExpression c = axiom.getClassExpression();

		try {
			if( ind.isAnonymous() )
				isEntailed = reasoner.isSatisfiable( c );
			else
				isEntailed = reasoner.hasType( ind.asNamedIndividual(), c, false );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		try {
			final OWLIndividual ind = axiom.getSubject();
			if( ind.isAnonymous() )
				exception = new UnsupportedReasonerOperationException(
						"Unsupported entailment check: anonymous individual in data property asserion" );
			else
				isEntailed = reasoner.hasDataPropertyRelationship( ind.asNamedIndividual(), axiom
						.getProperty(), axiom.getObject() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}

	}

	public void visit(OWLDataPropertyDomainAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLDataSomeValuesFrom( axiom
					.getProperty(), factory.getTopDatatype() ), axiom.getDomain() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLDataPropertyRangeAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLThing(), factory
					.getOWLDataAllValuesFrom( axiom.getProperty(), axiom.getRange() ) );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLSubDataPropertyOfAxiom axiom) {
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

		ArrayList<OWLNamedIndividual> list = new ArrayList<OWLNamedIndividual>();
		for( OWLIndividual ind : axiom.getIndividuals() ) {
			if( !ind.isAnonymous() )
				list.add( ind.asNamedIndividual() );
			else
				log.warning( "Ignoring anonymous individual in different individuals axiom" );
		}
		try {
			for( int i = 0; i < list.size() - 1; i++ ) {

				OWLNamedIndividual head = list.get( i );
				for( int j = i + 1; j < list.size(); j++ ) {
					OWLNamedIndividual next = list.get( j );

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

		int n = axiom.getClassExpressions().size();
		OWLClassExpression[] classes = axiom.getClassExpressions().toArray(
				new OWLClassExpression[n] );
		try {
			for( int i = 0; i < n - 1; i++ ) {
				for( int j = i + 1; j < n; j++ ) {
					OWLClassExpression notj = factory.getOWLObjectComplementOf( classes[j] );
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

	public void visit(OWLEquivalentClassesAxiom axiom) {
		isEntailed = true;

		try {
			Iterator<OWLClassExpression> i = axiom.getClassExpressions().iterator();
			if( i.hasNext() ) {
				OWLClassExpression first = i.next();

				while( i.hasNext() && isEntailed ) {
					OWLClassExpression next = i.next();

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
		final OWLIndividual ind = axiom.getSubject();
		if( ind.isAnonymous() )
			exception = new UnsupportedReasonerOperationException(
					"Unsupported entailment check: anonymous individual in negative data property asserion" );
		else {
			OWLClassExpression hasValue = factory.getOWLDataHasValue( axiom.getProperty(), axiom
					.getObject() );
			OWLClassExpression doesNotHaveValue = factory.getOWLObjectComplementOf( hasValue );
			try {
				isEntailed = reasoner.hasType( ind.asNamedIndividual(), doesNotHaveValue, false );
			} catch( OWLReasonerException e ) {
				exception = e;
			}
		}
	}

	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		final OWLIndividual ind = axiom.getSubject();
		final OWLIndividual o = axiom.getObject();
		if( ind.isAnonymous() || o.isAnonymous() )
			exception = new UnsupportedReasonerOperationException(
					"Unsupported entailment check: anonymous individual in negative object property asserion" );
		else {
			OWLClassExpression hasValue = factory.getOWLObjectHasValue( axiom.getProperty(), axiom
					.getObject() );
			OWLClassExpression doesNotHaveValue = factory.getOWLObjectComplementOf( hasValue );
			try {
				isEntailed = reasoner.hasType( ind.asNamedIndividual(), doesNotHaveValue, false );
			} catch( OWLReasonerException e ) {
				exception = e;
			}
		}
	}

	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		final OWLIndividual ind = axiom.getSubject();
		final OWLIndividual o = axiom.getObject();
		if( ind.isAnonymous() || o.isAnonymous() )
			exception = new UnsupportedReasonerOperationException(
					"Unsupported entailment check: anonymous individual in object property asserion" );
		else {
			try {
				isEntailed = reasoner.hasObjectPropertyRelationship( ind.asNamedIndividual(), axiom
						.getProperty(), o.asNamedIndividual() );
			} catch( OWLReasonerException e ) {
				exception = e;
			}
		}
	}

	public void visit(OWLSubPropertyChainOfAxiom axiom) {
		throw new UnsupportedOperationException();
	}

	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLObjectSomeValuesFrom( axiom
					.getProperty(), factory.getOWLThing() ), axiom.getDomain() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		try {
			isEntailed = reasoner.isSubClassOf( factory.getOWLThing(), factory
					.getOWLObjectAllValuesFrom( axiom.getProperty(), axiom.getRange() ) );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: object sub properties" );
	}

	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		try {
			isEntailed = reasoner.isReflexive( (OWLObjectProperty) axiom.getProperty() );
		} catch( OWLReasonerException e ) {
			exception = e;
		}
	}

	public void visit(OWLSameIndividualAxiom axiom) {
		isEntailed = true;

		ArrayList<OWLNamedIndividual> list = new ArrayList<OWLNamedIndividual>();
		for( OWLIndividual ind : axiom.getIndividuals() ) {
			if( !ind.isAnonymous() )
				list.add( ind.asNamedIndividual() );
			else
				log.warning( "Ignoring anonymous individual in different individuals axiom" );
		}
		try {
			Iterator<OWLNamedIndividual> i = list.iterator();
			if( i.hasNext() ) {
				OWLNamedIndividual first = i.next();

				while( i.hasNext() ) {
					OWLNamedIndividual next = i.next();

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

	public void visit(OWLSubClassOfAxiom axiom) {
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
		exception = new UnsupportedReasonerOperationException(
		"Unsupported entailment check: datatype definition axiom" );
	}

	public void visit(OWLDatatypeDefinitionAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: datatype definition axiom" );
	}

	public void visit(OWLHasKeyAxiom axiom) {
		exception = new UnsupportedReasonerOperationException(
				"Unsupported entailment check: key axiom" );
	}

	public void visit(OWLAnnotationAssertionAxiom axiom) {
		isEntailed = true;
	}

	public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
		isEntailed = true;
	}

	public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
		isEntailed = true;
	}

	public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		isEntailed = true;
	}
}
