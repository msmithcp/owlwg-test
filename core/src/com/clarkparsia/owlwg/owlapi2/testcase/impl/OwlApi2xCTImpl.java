package com.clarkparsia.owlwg.owlapi2.testcase.impl;

import java.util.EnumMap;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.io.StringInputSource;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.testcase.AbstractPremisedTest;
import com.clarkparsia.owlwg.testcase.OntologyParseException;
import com.clarkparsia.owlwg.testcase.PremisedTest;
import com.clarkparsia.owlwg.testcase.SerializationFormat;

/**
 * <p>
 * Title: OWLAPIv2 xConsistency Test Case Base Class
 * </p>
 * <p>
 * Description: Extended for consistency and inconsistency cases
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
public abstract class OwlApi2xCTImpl extends AbstractPremisedTest<OWLOntology> implements
		PremisedTest<OWLOntology>, OwlApi2Case {

	private final OWLOntologyManager						manager;
	private final EnumMap<SerializationFormat, OWLOntology>	parsedPremise;

	public OwlApi2xCTImpl(OWLOntology ontology, OWLIndividual i) {
		super( ontology, i );

		parsedPremise = new EnumMap<SerializationFormat, OWLOntology>( SerializationFormat.class );
		manager = OWLManager.createOWLOntologyManager();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return manager;
	}

	public OWLOntology parsePremiseOntology(SerializationFormat format)
			throws OntologyParseException {
		try {
			ImportsHelper.loadImports( this );
			OWLOntology o = parsedPremise.get( format );
			if( o == null ) {
				String l = getPremiseOntology( format );
				if( l == null )
					return null;

				StringInputSource source = new StringInputSource( l );
				o = getOWLOntologyManager().loadOntology( source );
				parsedPremise.put( format, o );
			}
			return o;
		} catch( OWLOntologyCreationException e ) {
			throw new OntologyParseException( e );
		}
	}
}
