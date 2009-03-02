package com.clarkparsia.owlwg.testrun;

import com.clarkparsia.owlwg.runner.TestRunner;
import com.clarkparsia.owlwg.testcase.TestCase;

/**
 * <p>
 * Title: Abstract Run
 * </p>
 * <p>
 * Description: Base implementation used by other {@link TestRunResult}
 * implementations
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
public abstract class AbstractRun implements TestRunResult {

	private final String		details;
	private final TestRunner	runner;
	private final TestCase		testcase;
	private final RunResultType	type;

	public AbstractRun(TestCase testcase, RunResultType type, TestRunner runner, String details) {
		if( testcase == null )
			throw new NullPointerException();
		if( type == null )
			throw new NullPointerException();
		if( runner == null )
			throw new NullPointerException();

		this.testcase = testcase;
		this.type = type;
		this.runner = runner;
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

	public RunResultType getResultType() {
		return type;
	}

	public TestCase getTestCase() {
		return testcase;
	}

	public TestRunner getTestRunner() {
		return runner;
	}

}
