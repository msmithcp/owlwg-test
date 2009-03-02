package com.clarkparsia.owlwg.testcase;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owl.io.StringInputSource;
import org.semanticweb.owl.model.OWLConstant;
import org.semanticweb.owl.model.OWLDataPropertyExpression;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;

/**
 * <p>
 * Title: Abstract Entailment Test Case
 * </p>
 * <p>
 * Description: Common base implementation shared by positive and negative
 * entailment tests
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
public abstract class AbstractEntailmentTest extends AbstractPremisedTest {

	private final EnumSet<SerializationFormat>				conclusionFormats;
	private final EnumMap<SerializationFormat, OWLOntology>	conclusionOntology;
	private final EnumMap<SerializationFormat, String>		conclusionOntologyLiteral;

	public AbstractEntailmentTest(OWLOntology ontology, OWLIndividual i, boolean positive) {
		super( ontology, i );

		conclusionFormats = EnumSet.noneOf( SerializationFormat.class );
		conclusionOntologyLiteral = new EnumMap<SerializationFormat, String>(
				SerializationFormat.class );
		conclusionOntology = new EnumMap<SerializationFormat, OWLOntology>(
				SerializationFormat.class );

		Map<OWLDataPropertyExpression, Set<OWLConstant>> values = i
				.getDataPropertyValues( ontology );

		for( SerializationFormat f : SerializationFormat.values() ) {
			Set<OWLConstant> conclusions = values.get( positive
				? f.getConclusionOWLDataProperty()
				: f.getNonConclusionOWLDataProperty() );
			if( conclusions != null ) {
				if( conclusions.size() != 1 )
					throw new IllegalArgumentException( f.toString() );
				conclusionOntologyLiteral.put( f, conclusions.iterator().next().getLiteral() );
				conclusionFormats.add( f );
			}
		}
	}

	public Set<SerializationFormat> getConclusionFormats() {
		return Collections.unmodifiableSet( conclusionFormats );
	}

	public String getConclusionOntology(SerializationFormat format) {
		return conclusionOntologyLiteral.get( format );
	}

	public OWLOntology parseConclusionOntology(SerializationFormat format)
			throws OWLOntologyCreationException {
		OWLOntology o = conclusionOntology.get( format );
		if( o == null ) {
			String l = conclusionOntologyLiteral.get( format );
			if( l == null )
				return null;

			StringInputSource source = new StringInputSource( l );
			o = getOWLOntologyManager().loadOntology( source );
			conclusionOntology.put( format, o );
		}
		return o;
	}
}
