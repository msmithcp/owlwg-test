package com.clarkparsia.owlwg.runner;

import java.net.URI;
import java.util.Collection;

import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testrun.TestRunResult;

/**
 * <p>
 * Title: Test Runner
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
public interface TestRunner {

	/**
	 * Get a URI identifying the test runner. Used as the target of {@code
	 * results:runner} object property assertions.
	 * 
	 * @return {@link URI} identifying the test runner
	 */
	public URI getURI();

	/**
	 * Run a test case with this runner. May produce multiple results because a
	 * single test case can be used in multiple ways. E.g., a single positive
	 * entailment test can be used as a profile identification test, a
	 * consistency test, and a positive entailment test.
	 * 
	 * @param testcase
	 * @param timeout
	 *            in milliseconds
	 * @return a collection of {@link TestRunResult} objects describing all
	 *         tests attempted.
	 */
	public Collection<TestRunResult> run(TestCase testcase, long timeout);
}
