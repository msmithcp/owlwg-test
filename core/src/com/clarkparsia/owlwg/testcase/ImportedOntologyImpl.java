package com.clarkparsia.owlwg.testcase;

import static com.clarkparsia.owlwg.testcase.TestVocabulary.ObjectProperty.IMPORTED_ONTOLOGY_IRI;
import static java.lang.String.format;

import java.net.URI;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owl.model.OWLConstant;
import org.semanticweb.owl.model.OWLDataPropertyExpression;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectPropertyExpression;
import org.semanticweb.owl.model.OWLOntology;

/**
 * <p>
 * Title: Imported Ontology Implementation
 * </p>
 * <p>
 * Description: Default implementation of {@link ImportedOntology}, which
 * queries an {@link OWLOntology} for details of the imported ontology, such as
 * serializations and normative formats.
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
public class ImportedOntologyImpl implements ImportedOntology {

	private static final Logger							log;

	static {
		log = Logger.getLogger( ImportedOntologyImpl.class.getCanonicalName() );
	}

	private final EnumSet<SerializationFormat>			formats;
	private final EnumMap<SerializationFormat, String>	ontologyLiteral;
	private final URI									uri;

	public ImportedOntologyImpl(OWLOntology ontology, OWLIndividual i) {
		Map<OWLObjectPropertyExpression, Set<OWLIndividual>> opValues = i
				.getObjectPropertyValues( ontology );

		Set<OWLIndividual> iris = opValues.get( IMPORTED_ONTOLOGY_IRI.getOWLObjectProperty() );
		if( iris == null ) {
			final String msg = format( "Value for property %s missing for imported ontology %s",
					IMPORTED_ONTOLOGY_IRI.getOWLObjectProperty().getURI(), i.getURI() );
			log.warning( msg );
			throw new NullPointerException( msg );
		}
		else if( iris.size() != 1 ) {
			final String msg = format(
					"Property %s should have a single value for imported ontology %s, but has %d",
					IMPORTED_ONTOLOGY_IRI.getOWLObjectProperty().getURI(), i.getURI(), iris.size() );
			log.warning( msg );
			throw new IllegalArgumentException();
		}
		else {
			uri = iris.iterator().next().getURI();
		}

		Map<OWLDataPropertyExpression, Set<OWLConstant>> values = i
				.getDataPropertyValues( ontology );

		formats = EnumSet.noneOf( SerializationFormat.class );
		ontologyLiteral = new EnumMap<SerializationFormat, String>( SerializationFormat.class );
		for( SerializationFormat f : SerializationFormat.values() ) {
			Set<OWLConstant> literals = values.get( f.getInputOWLDataProperty() );
			if( literals != null ) {
				if( literals.size() > 1 ) {
					log
							.warning( format(
									"Multiple ontologies found for imported ontology (%s) with serialization format (%s).  Choosing arbitrarily.",
									i.getURI(), f ) );
				}
				ontologyLiteral.put( f, literals.iterator().next().getLiteral() );
				formats.add( f );
			}
		}

	}

	public Set<SerializationFormat> getFormats() {
		return Collections.unmodifiableSet( formats );
	}

	public String getOntology(SerializationFormat format) {
		return ontologyLiteral.get( format );
	}

	public URI getURI() {
		return uri;
	}

}
