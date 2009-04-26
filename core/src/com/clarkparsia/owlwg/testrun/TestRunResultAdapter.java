package com.clarkparsia.owlwg.testrun;

import static com.clarkparsia.owlwg.testrun.ResultVocabulary.Class.TEST_RUN;
import static com.clarkparsia.owlwg.testrun.ResultVocabulary.ObjectProperty.SYNTAX_CONSTRAINT;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLIndividual;

import com.clarkparsia.owlwg.testcase.TestVocabulary;
import com.clarkparsia.owlwg.testrun.ResultVocabulary.AnnotationProperty;
import com.clarkparsia.owlwg.testrun.ResultVocabulary.ObjectProperty;

/**
 * <p>
 * Title: Test Run Result Adapter
 * </p>
 * <p>
 * Description: Convert test run objects to OWLAPI object model
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
public class TestRunResultAdapter {

	private class RunTypeAdapter implements TestRunResultVisitor {

		private List<OWLAxiom>	axioms;

		public List<OWLAxiom> process(TestRunResult result) {
			axioms = new ArrayList<OWLAxiom>();
			result.accept( this );
			return axioms;
		}

		public void visit(SyntaxConstraintRun result) {
			axioms.add( dataFactory.getOWLClassAssertionAxiom( currentIndividual, result
					.getTestType().getOWLClass() ) );
			axioms.add( dataFactory.getOWLObjectPropertyAssertionAxiom( currentIndividual,
					SYNTAX_CONSTRAINT.getOWLObjectProperty(), result.getConstraint()
							.getOWLIndividual() ) );
		}

		public void visit(ReasoningRun result) {
			axioms.add( dataFactory.getOWLClassAssertionAxiom( currentIndividual, result
					.getTestType().getOWLClass() ) );
		}

		public void visit(SyntaxTranslationRun result) {
			axioms.add( dataFactory.getOWLClassAssertionAxiom( currentIndividual, result
					.getTestType().getOWLClass() ) );
		}

	}

	private static Integer	bnodeid;

	static {
		bnodeid = Integer.valueOf( 0 );
	}

	private static URI mintBNode() {
		bnodeid++;
		return URI.create( String.format( "testrunadapter_%d", bnodeid ) );
	}

	private OWLIndividual			currentIndividual;
	private final OWLDataFactory	dataFactory;
	private final RunTypeAdapter	runTypeAdapter;

	public TestRunResultAdapter(OWLDataFactory dataFactory) {
		if( dataFactory == null )
			throw new NullPointerException();

		this.dataFactory = dataFactory;
		runTypeAdapter = new RunTypeAdapter();
	}

	public Collection<OWLAxiom> asOWLAxioms(TestRunResult r, OWLIndividual i) {
		if( r == null )
			throw new NullPointerException();

		List<OWLAxiom> axioms = new ArrayList<OWLAxiom>();

		currentIndividual = i;
		axioms.add( dataFactory.getOWLClassAssertionAxiom( currentIndividual, TEST_RUN
				.getOWLClass() ) );
		axioms.add( dataFactory.getOWLClassAssertionAxiom( currentIndividual, r.getResultType()
				.getOWLClass() ) );
		axioms.add( dataFactory.getOWLObjectPropertyAssertionAxiom( currentIndividual,
				ObjectProperty.RUNNER.getOWLObjectProperty(), dataFactory.getOWLIndividual( r
						.getTestRunner().getURI() ) ) );

		OWLIndividual testAnonIndividual = dataFactory.getOWLAnonymousIndividual( mintBNode() );

		axioms.add( dataFactory.getOWLObjectPropertyAssertionAxiom( currentIndividual,
				ObjectProperty.TEST.getOWLObjectProperty(), testAnonIndividual ) );
		axioms.add( dataFactory.getOWLDataPropertyAssertionAxiom( testAnonIndividual,
				TestVocabulary.DatatypeProperty.IDENTIFIER.getOWLDataProperty(), dataFactory
						.getOWLTypedConstant( r.getTestCase().getIdentifier() ) ) );
		final String details = r.getDetails();
		if( details != null && details.length() > 0 ) {
			axioms.add( dataFactory.getOWLEntityAnnotationAxiom( i, dataFactory
					.getOWLConstantAnnotation( AnnotationProperty.DETAILS
							.getAnnotationPropertyURI(), dataFactory
							.getOWLUntypedConstant( details ) ) ) );
		}

		axioms.addAll( runTypeAdapter.process( r ) );

		return axioms;
	}
}
