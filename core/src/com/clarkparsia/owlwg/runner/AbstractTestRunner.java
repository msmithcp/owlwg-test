package com.clarkparsia.owlwg.runner;

import static com.clarkparsia.owlwg.testcase.SerializationFormat.RDFXML;
import static com.clarkparsia.owlwg.testrun.RunResultType.FAILING;
import static com.clarkparsia.owlwg.testrun.RunResultType.INCOMPLETE;
import static com.clarkparsia.owlwg.testrun.RunResultType.PASSING;
import static com.clarkparsia.owlwg.testrun.RunTestType.CONSISTENCY;
import static com.clarkparsia.owlwg.testrun.RunTestType.INCONSISTENCY;
import static com.clarkparsia.owlwg.testrun.RunTestType.NEGATIVE_ENTAILMENT;
import static com.clarkparsia.owlwg.testrun.RunTestType.POSITIVE_ENTAILMENT;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.testcase.AbstractEntailmentTest;
import com.clarkparsia.owlwg.testcase.AbstractPremisedTest;
import com.clarkparsia.owlwg.testcase.ConsistencyTest;
import com.clarkparsia.owlwg.testcase.InconsistencyTest;
import com.clarkparsia.owlwg.testcase.NegativeEntailmentTest;
import com.clarkparsia.owlwg.testcase.PositiveEntailmentTest;
import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testcase.TestCaseVisitor;
import com.clarkparsia.owlwg.testrun.ReasoningRun;
import com.clarkparsia.owlwg.testrun.RunTestType;
import com.clarkparsia.owlwg.testrun.TestRunResult;

/**
 * <p>
 * Title: Abstract Test Runner
 * </p>
 * <p>
 * Description: Base test runner implementation intended to encapsulate
 * non-interesting bits of the test runner and make reuse and runner
 * implementation easier. Handles test type-specific behavior and timeout
 * enforcement.
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
public abstract class AbstractTestRunner implements TestRunner {

	protected abstract class AbstractTestAsRunnable<T extends TestCase> implements TestAsRunnable {

		protected TestRunResult		result;
		protected final T			testcase;
		protected Throwable			throwable;
		protected final RunTestType	type;

		public AbstractTestAsRunnable(T testcase, RunTestType type) {
			this.testcase = testcase;

			if( !EnumSet.of( CONSISTENCY, INCONSISTENCY, NEGATIVE_ENTAILMENT, POSITIVE_ENTAILMENT )
					.contains( type ) )
				throw new IllegalArgumentException();

			this.type = type;
			result = null;
			throwable = null;
		}

		public TestRunResult getErrorResult(Throwable th) {
			return new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this, th
					.getMessage() );
		}

		public TestRunResult getResult() throws Throwable {
			if( throwable != null )
				throw throwable;
			if( result == null )
				throw new IllegalStateException();

			return result;
		}

		public TestRunResult getTimeoutResult() {
			return new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this, String
					.format( "Timeout: %s ms", timeout ) );
		}
	}

	private class Runner implements TestCaseVisitor {

		private TestRunResult[]	results;

		public List<TestRunResult> getResults(TestCase testcase) {
			results = null;
			testcase.accept( this );
			return Arrays.asList( results );
		}

		public void visit(ConsistencyTest testcase) {
			results = new TestRunResult[1];
			results[0] = runConsistencyTest( testcase );
		}

		public void visit(InconsistencyTest testcase) {
			results = new TestRunResult[1];
			results[0] = runInconsistencyTest( testcase );
		}

		public void visit(NegativeEntailmentTest testcase) {
			results = new TestRunResult[2];
			results[0] = runConsistencyTest( testcase );
			results[1] = runEntailmentTest( testcase );
		}

		public void visit(PositiveEntailmentTest testcase) {
			results = new TestRunResult[2];
			results[0] = runConsistencyTest( testcase );
			results[1] = runEntailmentTest( testcase );
		}
	}

	protected interface TestAsRunnable extends Runnable {
		public TestRunResult getErrorResult(Throwable th);

		public TestRunResult getResult() throws Throwable;

		public TestRunResult getTimeoutResult();
	}

	protected class XConsistencyTest extends AbstractTestAsRunnable<AbstractPremisedTest> {

		public XConsistencyTest(AbstractPremisedTest testcase, RunTestType type) {
			super( testcase, type );

			if( !EnumSet.of( CONSISTENCY, INCONSISTENCY ).contains( type ) )
				throw new IllegalArgumentException();
		}

		public void run() {
			if( !testcase.getPremiseFormats().contains( RDFXML ) )
				result = new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this,
						"Only RDF/XML input ontology parsing supported." );

			OWLOntology o;
			try {
				o = testcase.parsePremiseOntology( RDFXML );
			} catch( OWLOntologyCreationException e ) {
				result = new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this,
						"Exception parsing premise ontology: " + e.getLocalizedMessage() );
				return;
			}

			try {
				final boolean consistent = isConsistent( testcase.getOWLOntologyManager(), o );
				if( consistent )
					result = new ReasoningRun( testcase, CONSISTENCY.equals( type )
						? PASSING
						: FAILING, type, AbstractTestRunner.this );
				else
					result = new ReasoningRun( testcase, INCONSISTENCY.equals( type )
						? PASSING
						: FAILING, type, AbstractTestRunner.this );
			} catch( Throwable th ) {
				result = new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this,
						"Caught throwable: " + th.getLocalizedMessage() );
			}
		}

	}

	protected class XEntailmentTest extends AbstractTestAsRunnable<AbstractEntailmentTest> {

		public XEntailmentTest(AbstractEntailmentTest testcase, RunTestType type) {
			super( testcase, type );

			if( !EnumSet.of( POSITIVE_ENTAILMENT, NEGATIVE_ENTAILMENT ).contains( type ) )
				throw new IllegalArgumentException();
		}

		public void run() {
			if( !testcase.getPremiseFormats().contains( RDFXML ) )
				result = new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this,
						"Only RDF/XML input ontology parsing supported." );
			if( !testcase.getConclusionFormats().contains( RDFXML ) )
				result = new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this,
						"Only RDF/XML input ontology parsing supported." );

			OWLOntology premise, conclusion;
			try {
				premise = testcase.parsePremiseOntology( RDFXML );
				conclusion = testcase.parseConclusionOntology( RDFXML );
			} catch( OWLOntologyCreationException e ) {
				result = new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this,
						"Exception parsing input ontology: " + e.getLocalizedMessage() );
				return;
			}

			try {
				boolean entailed = isEntailed( testcase.getOWLOntologyManager(), premise,
						conclusion );
				if( entailed )
					result = new ReasoningRun( testcase, POSITIVE_ENTAILMENT.equals( type )
						? PASSING
						: FAILING, type, AbstractTestRunner.this );
				else
					result = new ReasoningRun( testcase, NEGATIVE_ENTAILMENT.equals( type )
						? PASSING
						: FAILING, type, AbstractTestRunner.this );
			} catch( Throwable th ) {
				result = new ReasoningRun( testcase, INCOMPLETE, type, AbstractTestRunner.this,
						"Caught throwable: " + th.getLocalizedMessage() );
			}
		}
	}

	private final Runner	runner;
	private long			timeout;

	public AbstractTestRunner() {
		runner = new Runner();
	}

	protected abstract boolean isConsistent(OWLOntologyManager manager, OWLOntology o)
			throws OWLReasonerException;

	protected abstract boolean isEntailed(OWLOntologyManager manager, OWLOntology premise,
			OWLOntology conclusion) throws OWLReasonerException;

	protected TestRunResult run(TestAsRunnable runnable) {
		Thread t = new Thread( runnable );
		t.start();
		try {
			t.join( timeout );
		} catch( InterruptedException e ) {
			return runnable.getErrorResult( e );
		}
		if( t.isAlive() ) {
			try {
				return runnable.getTimeoutResult();
			} finally {
				t.stop();
			}
		}
		else {
			try {
				return runnable.getResult();
			} catch( Throwable th ) {
				return runnable.getErrorResult( th );
			}
		}
	}

	public Collection<TestRunResult> run(TestCase testcase, long timeout) {
		this.timeout = timeout;
		return runner.getResults( testcase );
	}

	protected TestRunResult runConsistencyTest(AbstractPremisedTest testcase) {
		return run( new XConsistencyTest( testcase, CONSISTENCY ) );
	}

	protected TestRunResult runEntailmentTest(NegativeEntailmentTest testcase) {
		return run( new XEntailmentTest( testcase, NEGATIVE_ENTAILMENT ) );
	}

	protected TestRunResult runEntailmentTest(PositiveEntailmentTest testcase) {
		return run( new XEntailmentTest( testcase, POSITIVE_ENTAILMENT ) );
	}

	protected TestRunResult runInconsistencyTest(AbstractPremisedTest testcase) {
		return run( new XConsistencyTest( testcase, INCONSISTENCY ) );
	}
}
