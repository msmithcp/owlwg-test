package com.clarkparsia.owlwg;

import java.io.File;
import java.net.URI;

/**
 * <p>
 * Title: Constants
 * </p>
 * <p>
 * Description: Constants used by multiple classes
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
public class Constants {

	public static final URI	RESULTS_ONTOLOGY_PHYSICAL_URI;
	public static final URI	TEST_ONTOLOGY_PHYSICAL_URI;

	static {
		File f = new File( "ontologies/test-ontology.owl" );
		TEST_ONTOLOGY_PHYSICAL_URI = f.toURI();

		f = new File( "ontologies/results-ontology.owl" );
		RESULTS_ONTOLOGY_PHYSICAL_URI = f.toURI();
	}
}
