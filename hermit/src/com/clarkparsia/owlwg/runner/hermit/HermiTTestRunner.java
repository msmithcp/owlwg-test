package com.clarkparsia.owlwg.runner.hermit;

import java.net.URI;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.owlwg.Harness;
import com.clarkparsia.owlwg.runner.OWLReasonerTestRunner;

public class HermiTTestRunner extends OWLReasonerTestRunner {

    public HermiTTestRunner() {
        super(new Reasoner.ReasonerFactory(), URI
                .create("http://hermit-reasoner.com/"));
    }

    @Override
    protected boolean isEntailed(OWLOntologyManager manager,
            OWLOntology premise, OWLOntology conclusion)
            throws OWLReasonerException {

        Reasoner reasoner = new Reasoner(new Configuration(), manager, premise);
        EntailmentChecker checker = new EntailmentChecker(reasoner, manager
                .getOWLDataFactory());
        for (OWLAxiom axiom : conclusion.getLogicalAxioms()) {
            if (!checker.isEntailed(axiom))
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        System.setProperty(Harness.TEST_RUNNER_CLASS_PROPERTY, "com.clarkparsia.owlwg.runner.hermit.HermiTTestRunner");    
        Harness.main(new String[] {"http://wiki.webont.org/exports/all.rdf"});
    }
    
}
