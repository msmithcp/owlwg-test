package com.clarkparsia.owlwg.runner.hermit;

import java.net.URI;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.EntailmentChecker;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.inference.OWLReasonerException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.owlwg.Harness;
import com.clarkparsia.owlwg.owlapi3.runner.impl.OwlApi3AbstractRunner;
import com.clarkparsia.owlwg.owlapi3.testcase.impl.OwlApi3TestCaseFactory;

public class HermiTTestRunner extends OwlApi3AbstractRunner {

    private static final URI s_uri;

    static {
        s_uri = URI.create("http://hermit-reasoner.com/");
    }

    public String getName() {
        return "HermiT";
    }

    public URI getURI() {
        return s_uri;
    }

    @Override
    protected boolean isConsistent(OWLOntologyManager manager, OWLOntology o)
            throws OWLReasonerException {
        Reasoner reasoner = new Reasoner(new Configuration(), manager, o);
        return reasoner.isConsistent();
    }

    @Override
    protected boolean isEntailed(OWLOntologyManager manager,
            OWLOntology premise, OWLOntology conclusion)
            throws OWLReasonerException {

        Reasoner reasoner = new Reasoner(new Configuration(), manager, premise);
        EntailmentChecker checker = new EntailmentChecker(reasoner, manager.getOWLDataFactory());
        return checker.entails(conclusion.getLogicalAxioms());
    }

    public static void main(String[] args) throws Exception {
        System.setProperty( Harness.TEST_RUNNER_CLASS_PROPERTY, com.clarkparsia.owlwg.runner.hermit.HermiTTestRunner.class.getCanonicalName());
        System.setProperty( Harness.TEST_FACTORY_CLASS_PROPERTY, OwlApi3TestCaseFactory.class.getCanonicalName() );
        // http://wiki.webont.org/exports/approved/type-consistency.rdf
        // http://wiki.webont.org/exports/all.rdf
        // http://km.aifb.uni-karlsruhe.de/projects/owltests/index.php/Special:Ask/-5B-5B:New-2DFeature-2DDisjointObjectProperties-2D001-5D-5D/format%3Dowltest/title%3DNew-2DFeature-2DDisjointObjectProperties-2D001
        System.out.println("Results will be written to: /Users/bglimm/Documents/workspace/owlwg-test/HermiT-results.txt");
        Harness.main(new String[] { "http://km.aifb.uni-karlsruhe.de/projects/owltests/index.php/Special:Ask/-5B-5B:New-2DFeature-2DDisjointObjectProperties-2D001-5D-5D/format%3Dowltest/title%3DNew-2DFeature-2DDisjointObjectProperties-2D001", "-o/Users/bglimm/Documents/workspace/owlwg-test/HermiT-results2.txt", "-t300000" });
    }

}
