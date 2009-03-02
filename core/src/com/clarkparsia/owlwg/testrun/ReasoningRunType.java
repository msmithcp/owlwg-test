package com.clarkparsia.owlwg.testrun;

import static com.clarkparsia.owlwg.testrun.ResultVocabulary.Class.CONSISTENCY_RUN;
import static com.clarkparsia.owlwg.testrun.ResultVocabulary.Class.INCONSISTENCY_RUN;
import static com.clarkparsia.owlwg.testrun.ResultVocabulary.Class.NEGATIVE_ENTAILMENT_RUN;
import static com.clarkparsia.owlwg.testrun.ResultVocabulary.Class.POSITIVE_ENTAILMENT_RUN;

import org.semanticweb.owl.model.OWLClass;

/**
 * <p>
 * Title: Reasoning Run Type
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
public enum ReasoningRunType {

	CONSISTENCY(CONSISTENCY_RUN), INCONSISTENCY(INCONSISTENCY_RUN),
	NEGATIVE_ENTAILMENT(NEGATIVE_ENTAILMENT_RUN), POSITIVE_ENTAILMENT(POSITIVE_ENTAILMENT_RUN);

	private final OWLClass	c;

	private ReasoningRunType(ResultVocabulary.Class c) {
		this.c = c.getOWLClass();
	}

	public OWLClass getOWLClass() {
		return c;
	}
}
