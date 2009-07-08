package com.clarkparsia.owlwg.owlapi3.testcase.impl;

import static java.lang.String.format;

import java.net.URI;
import java.util.logging.Logger;

import org.semanticweb.owlapi.io.StringInputSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.owlwg.testcase.SerializationFormat;

/**
 * <p>
 * Title: OWLAPIv3 Imports Helper
 * </p>
 * <p>
 * Description: Static implementation used to load imports for a test case into
 * the ontology manager
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
public class ImportsHelper {

	private final static Logger	log;

	static {
		log = Logger.getLogger( ImportsHelper.class.getCanonicalName() );
	}

	public static void loadImports(OwlApi3Case t) throws OWLOntologyCreationException {
		final OWLOntologyManager manager = t.getOWLOntologyManager();

		for( URI u : t.getImportedOntologies() ) {
			if( !manager.contains( IRI.create( u ) ) ) {
				String str = t.getImportedOntology( u, SerializationFormat.RDFXML );
				if( str == null ) {
					final String msg = format(
							"Imported ontology (%s) not provided in RDF/XML syntax for testcase (%s)",
							u, t.getIdentifier() );
					log.warning( msg );
					throw new OWLOntologyCreationException( msg );
				}
				else {
					StringInputSource source = new StringInputSource( str );
					try {
						manager.loadOntology( source );
					} catch( OWLOntologyCreationException e ) {
						log.warning( format( "Failed to parse imported ontology for testcase (%s)",
								t.getIdentifier() ) );
						throw e;
					}
				}
			}
		}
	}

}
