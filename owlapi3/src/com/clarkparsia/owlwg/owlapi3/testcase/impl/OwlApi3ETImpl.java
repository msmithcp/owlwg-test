package com.clarkparsia.owlwg.owlapi3.testcase.impl;
 
import java.util.EnumMap;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringInputSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.owlwg.testcase.AbstractEntailmentTest;
import com.clarkparsia.owlwg.testcase.EntailmentTest;
import com.clarkparsia.owlwg.testcase.OntologyParseException;
import com.clarkparsia.owlwg.testcase.SerializationFormat;
 
/**
* <p>
* Title: OWLAPIv3 Entailment Test Case Base Class
* </p>
* <p>
* Description: Extended for positive and negative entailment cases
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
public abstract class OwlApi3ETImpl extends AbstractEntailmentTest<OWLOntology> implements
    EntailmentTest<OWLOntology>, OwlApi3Case {
 
  private final OWLOntologyManager            manager;
  private final EnumMap<SerializationFormat, OWLOntology>  parsedConclusion;
  private final EnumMap<SerializationFormat, OWLOntology>  parsedPremise;
 
  public OwlApi3ETImpl(org.semanticweb.owl.model.OWLOntology ontology,
      org.semanticweb.owl.model.OWLIndividual i, boolean positive) {
    super( ontology, i, positive );
 
    parsedPremise = new EnumMap<SerializationFormat, OWLOntology>( SerializationFormat.class );
    parsedConclusion = new EnumMap<SerializationFormat, OWLOntology>( SerializationFormat.class );
    manager = OWLManager.createOWLOntologyManager();
  }
 
  public OWLOntologyManager getOWLOntologyManager() {
    return manager;
  }
 
  public OWLOntology parseConclusionOntology(SerializationFormat format)
      throws OntologyParseException {
    try {
      ImportsHelper.loadImports( this );
      OWLOntology o = parsedConclusion.get( format );
      if( o == null ) {
        String l = getConclusionOntology( format );
        if( l == null )
          return null;
 
        StringInputSource source = new StringInputSource( l );
        o = getOWLOntologyManager().loadOntology( source );
        parsedConclusion.put( format, o );
      }
      return o;
    } catch( OWLOntologyCreationException e ) {
      throw new OntologyParseException( e );
    }
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