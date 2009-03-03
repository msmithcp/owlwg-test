Source code in this package is provided under the terms of the GNU
Affero General Public License (AGPL) version 3 (see LICENSE.txt).

To run tests with the default test runner (Pellet)

  java com.clarkparsia.owlwg.Harness file:///tmp/my-file-of-tests.owl

To use a specific test runner set the Harness.TestRunner system property

  java \
    -DHarness.TestRunner=com.clarkparsia.owlwg.runner.hermit.HermiTTestRunner \
    com.clarkparsia.owlwg.Harness file:///tmp/my-file-of-tests.owl

The single argument should be a URL for a file containing one or more
test cases described using the vocabulary described in the OWL 2
Test and Conformance document at http://www.w3.org/TR/owl2-test/ and
serialized in RDF/XML.  file: and http: URI schemes are supported.

Test cases in this format are available at the OWL 2 Test Case Repository 
http://km.aifb.uni-karlsruhe.de/projects/owltests/index.php/OWL_2_Test_Cases
For each test case, the 'Download OWL' link exports the test case in
the appropriate format and can be used directly.

Comments to Michael Smith <msmith@clarkparsia.com>
