package com.clarkparsia.owlwg.runner;

import java.net.URI;
import java.util.Collection;

import org.semanticweb.owl.inference.OWLReasonerException;

import com.clarkparsia.owlwg.testcase.TestCase;
import com.clarkparsia.owlwg.testrun.TestRunResult;

/**
 * <p>
 * Title: Read Only Test Runner
 * </p>
 * <p>
 * Description: Test runner implementation that isn't capable of running tests,
 * but can be used when a read only object is needed (e.g., parsing results for
 * reporting).
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
public class ReadOnlyTestRunner implements TestRunner {

	public static ReadOnlyTestRunner testRunner(URI uri, String name) {
		return new ReadOnlyTestRunner( uri, name );
	}

	final private URI		uri;
	final private String	name;

	public ReadOnlyTestRunner(URI uri, String name) {
		this.uri = uri;
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if( this == obj )
			return true;

		if( obj instanceof ReadOnlyTestRunner ) {
			final ReadOnlyTestRunner other = (ReadOnlyTestRunner) obj;
			return this.uri.equals( other.uri );
		}

		return false;
	}

	public String getName() {
		return name;
	}

	public URI getURI() {
		return uri;
	}

	@Override
	public int hashCode() {
		return uri.hashCode();
	}

	public Collection<TestRunResult> run(TestCase testcase, long timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return uri.toString();
	}

	public void dispose() throws OWLReasonerException {
	}
}