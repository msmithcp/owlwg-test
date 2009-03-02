package com.clarkparsia.owlwg.testcase;

import static com.clarkparsia.owlwg.testcase.TestVocabulary.DatatypeProperty.IDENTIFIER;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.Individual.FULL;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.ObjectProperty.PROFILE;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.ObjectProperty.SEMANTICS;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.ObjectProperty.SPECIES;
import static com.clarkparsia.owlwg.testcase.TestVocabulary.ObjectProperty.STATUS;
import static java.util.Collections.unmodifiableSet;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLConstant;
import org.semanticweb.owl.model.OWLDataPropertyExpression;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectPropertyExpression;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.testcase.TestVocabulary.Individual;

/**
 * <p>
 * Title: Abstract Base Test Case
 * </p>
 * <p>
 * Description: Common base implementation shared by all test case
 * implementations.
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
public abstract class AbstractBaseTestCase implements TestCase {

	private final String					identifier;

	private final OWLOntologyManager		manager;

	private final EnumSet<SyntaxConstraint>	notsatisfied;

	private final EnumSet<Semantics>		notsemantics;

	private final EnumSet<SyntaxConstraint>	satisfied;

	private final EnumSet<Semantics>		semantics;

	private final Status					status;

	public AbstractBaseTestCase(OWLOntology ontology, OWLIndividual i) {

		Map<OWLDataPropertyExpression, Set<OWLConstant>> dpValues = i
				.getDataPropertyValues( ontology );
		Set<OWLConstant> identifiers = dpValues.get( IDENTIFIER.getOWLDataProperty() );
		if( identifiers == null )
			throw new NullPointerException();
		if( identifiers.size() != 1 )
			throw new IllegalArgumentException();

		identifier = identifiers.iterator().next().getLiteral();

		Map<OWLObjectPropertyExpression, Set<OWLIndividual>> opValues = i
				.getObjectPropertyValues( ontology );

		Set<OWLIndividual> statuses = opValues.get( STATUS.getOWLObjectProperty() );
		if( statuses == null || statuses.isEmpty() )
			status = null;
		else if( statuses.size() > 1 )
			throw new IllegalArgumentException();
		else {
			OWLIndividual s = statuses.iterator().next();
			status = Status.get( s );
			if( status == null )
				throw new NullPointerException( s.getURI().toASCIIString() );
		}

		satisfied = EnumSet.noneOf( SyntaxConstraint.class );
		Set<OWLIndividual> profiles = opValues.get( PROFILE.getOWLObjectProperty() );
		if( profiles != null ) {
			for( OWLIndividual p : profiles ) {
				SyntaxConstraint c = SyntaxConstraint.get( p );
				if( c == null )
					throw new NullPointerException( p.getURI().toASCIIString() );
				satisfied.add( c );
			}
		}

		Set<OWLIndividual> species = opValues.get( SPECIES.getOWLObjectProperty() );
		if( species != null ) {
			for( OWLIndividual s : species ) {
				if( FULL.getOWLIndividual().equals( s ) )
					continue;
				if( Individual.DL.getOWLIndividual().equals( s ) )
					satisfied.add( SyntaxConstraint.DL );
				else
					throw new IllegalArgumentException( s.getURI().toASCIIString() );
			}
		}

		semantics = EnumSet.noneOf( Semantics.class );
		Set<OWLIndividual> sems = opValues.get( SEMANTICS.getOWLObjectProperty() );
		if( sems != null ) {
			for( OWLIndividual sem : sems ) {
				Semantics s = Semantics.get( sem );
				if( s == null )
					throw new NullPointerException( sem.getURI().toASCIIString() );
				semantics.add( s );
			}
		}

		Map<OWLObjectPropertyExpression, Set<OWLIndividual>> nopValues = i
				.getNegativeObjectPropertyValues( ontology );

		notsatisfied = EnumSet.noneOf( SyntaxConstraint.class );

		Set<OWLIndividual> notprofiles = nopValues.get( PROFILE.getOWLObjectProperty() );
		if( notprofiles != null ) {
			for( OWLIndividual p : notprofiles ) {
				SyntaxConstraint c = SyntaxConstraint.get( p );
				if( c == null )
					throw new NullPointerException( p.getURI().toASCIIString() );
				notsatisfied.add( c );
			}
		}

		Set<OWLIndividual> notspecies = nopValues.get( SPECIES.getOWLObjectProperty() );
		if( notspecies != null ) {
			for( OWLIndividual s : notspecies ) {
				if( Individual.DL.getOWLIndividual().equals( s ) )
					notsatisfied.add( SyntaxConstraint.DL );
				else
					throw new IllegalArgumentException( s.getURI().toASCIIString() );
			}
		}

		notsemantics = EnumSet.noneOf( Semantics.class );
		Set<OWLIndividual> notsems = nopValues.get( SEMANTICS.getOWLObjectProperty() );
		if( notsems != null ) {
			for( OWLIndividual sem : notsems ) {
				Semantics s = Semantics.get( sem );
				if( s == null )
					throw new NullPointerException( sem.getURI().toASCIIString() );
				notsemantics.add( s );
			}
		}

		manager = OWLManager.createOWLOntologyManager();
	}

	public Set<Semantics> getApplicableSemantics() {
		return unmodifiableSet( semantics );
	}

	public String getIdentifier() {
		return identifier;
	}

	public Set<Semantics> getNotApplicableSemantics() {
		return unmodifiableSet( notsemantics );
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return manager;
	}

	public Set<SyntaxConstraint> getSatisfiedConstraints() {
		return unmodifiableSet( satisfied );
	}

	public Status getStatus() {
		return status;
	}

	public Set<SyntaxConstraint> getUnsatisfiedConstraints() {
		return unmodifiableSet( notsatisfied );
	}

}
