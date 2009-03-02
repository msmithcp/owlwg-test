package com.clarkparsia.owlwg.testrun;

import com.clarkparsia.owlwg.runner.TestRunner;
import com.clarkparsia.owlwg.testcase.TestCase;

/**
 * <p>
 * Title: Reasoning Run
 * </p>
 * <p>
 * Description:
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
public class ReasoningRun extends AbstractRun {

	private final ReasoningRunType	type;

	public ReasoningRun(TestCase testcase, ReasoningRunType semanticType, RunResultType resultType,
			TestRunner runner) {
		this( testcase, semanticType, resultType, runner, null );
	}

	public ReasoningRun(TestCase testcase, ReasoningRunType semanticType, RunResultType resultType,
			TestRunner runner, String details) {
		super( testcase, resultType, runner, details );
		this.type = semanticType;
	}

	public void accept(TestRunResultVisitor visitor) {
		visitor.visit( this );
	}

	public ReasoningRunType getSemanticRunType() {
		return type;
	}

	@Override
	public String toString() {
		String details = getDetails();
		if( details == null )
			return String.format( "Result( %s , %s, %s)", getTestCase(), getResultType(),
					getSemanticRunType() );
		else
			return String.format( "Result( %s , %s, %s (%s))", getTestCase(), getResultType(),
					getSemanticRunType(), details );
	}
}
