package com.clarkparsia.owlwg.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.clarkparsia.owlwg.runner.TestRunner;
import com.clarkparsia.owlwg.testcase.ConsistencyTest;
import com.clarkparsia.owlwg.testcase.InconsistencyTest;
import com.clarkparsia.owlwg.testcase.NegativeEntailmentTest;
import com.clarkparsia.owlwg.testcase.PositiveEntailmentTest;
import com.clarkparsia.owlwg.testcase.Status;
import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testcase.TestCaseVisitor;
import com.clarkparsia.owlwg.testrun.RunTestType;
import com.clarkparsia.owlwg.testrun.TestRunResult;

/**
 * <p>
 * Title: Utilities
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
public class Utilities {

	public static Collection<TestRunner> collectRunners(Collection<TestRunResult> results) {
		Set<TestRunner> set = new HashSet<TestRunner>();
		for( TestRunResult r : results )
			set.add( r.getTestRunner() );

		TestRunner[] arr = set.toArray( new TestRunner[0] );
		Arrays.sort( arr, new Comparator<TestRunner>() {
			public int compare(TestRunner o1, TestRunner o2) {
				return o1.getURI().compareTo( o2.getURI() );
			}
		} );

		return Arrays.asList( arr );
	}

	public static EnumSet<RunTestType> collectTestTypes(Collection<TestRunResult> results) {
		EnumSet<RunTestType> set = EnumSet.noneOf( RunTestType.class );
		for( TestRunResult r : results )
			set.add( r.getTestType() );
		return set;
	}

	public static Map<Status, Collection<TestCase>> indexByStatus(Iterable<TestCase> cases) {
		Map<Status, Collection<TestCase>> byStatus = new HashMap<Status, Collection<TestCase>>();
		for( Status s : Status.values() ) {
			byStatus.put( s, new ArrayList<TestCase>() );
		}
		byStatus.put( null, new ArrayList<TestCase>() );

		for( TestCase c : cases ) {
			Status s = c.getStatus();
			byStatus.get( s ).add( c );
		}
		return byStatus;
	}

	public static EnumSet<RunTestType> possibleRunTestTypes(TestCase c) {
		final EnumSet<RunTestType> set = EnumSet.noneOf( RunTestType.class );
		c.accept( new TestCaseVisitor() {

			public void visit(ConsistencyTest testcase) {
				set.add( RunTestType.CONSISTENCY );
			}

			public void visit(InconsistencyTest testcase) {
				set.add( RunTestType.INCONSISTENCY );
			}

			public void visit(NegativeEntailmentTest testcase) {
				set.add( RunTestType.CONSISTENCY );
				set.add( RunTestType.NEGATIVE_ENTAILMENT );
			}

			public void visit(PositiveEntailmentTest testcase) {
				set.add( RunTestType.CONSISTENCY );
				set.add( RunTestType.POSITIVE_ENTAILMENT );
			}

		} );
		return set;
	}

}
