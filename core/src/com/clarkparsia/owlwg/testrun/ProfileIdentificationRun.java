package com.clarkparsia.owlwg.testrun;

import com.clarkparsia.owlwg.runner.TestRunner;
import com.clarkparsia.owlwg.testcase.TestCase;

/**
 * <p>
 * Title: Profile Identification Run
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
public class ProfileIdentificationRun extends AbstractRun {

	public ProfileIdentificationRun(TestCase testcase, RunResultType type, TestRunner runner) {
		this( testcase, type, runner, null );
	}

	public ProfileIdentificationRun(TestCase testcase, RunResultType type, TestRunner runner,
			String details) {
		super( testcase, type, runner, details );
	}

	public void accept(TestRunResultVisitor visitor) {
		visitor.visit( this );
	}

}
