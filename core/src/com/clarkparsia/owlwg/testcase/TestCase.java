package com.clarkparsia.owlwg.testcase;

import java.net.URI;
import java.util.Set;

/**
 * <p>
 * Title: Test Case
 * </p>
 * <p>
 * Description: Interface based on test cases described at <a
 * href="http://www.w3.org/TR/owl2-test/">http://www.w3.org/TR/owl2-test/</a>.
 * Parameterized based on the object returned when parsing an ontology.
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
public interface TestCase<O> {

	public void accept(TestCaseVisitor<O> visitor);

	public Set<Semantics> getApplicableSemantics();

	public String getIdentifier();

	public Set<URI> getImportedOntologies();

	public String getImportedOntology(URI uri, SerializationFormat format);

	public Set<SerializationFormat> getImportedOntologyFormats(URI uri);

	public Set<Semantics> getNotApplicableSemantics();

	public Set<SyntaxConstraint> getSatisfiedConstraints();

	public Status getStatus();

	public Set<SyntaxConstraint> getUnsatisfiedConstraints();

	public URI getURI();
}
