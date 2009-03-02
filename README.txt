Source code in this package is provided under the terms of the GNU
Affero General Public License (AGPL) version 3 (see LICENSE.txt).

To run tests with the default test runner (Pellet)

  java com.clarkparsia.owlwg.Harness file:///tmp/my-file-of-tests.owl

To use a specific test runner set the Harness.TestRunner system property

  java \
    -DHarness.TestRunner=com.clarkparsia.owlwg.runner.hermit.HermiTTestRunner \
    file:///tmp/my-file-of-tests.owl


Comments to msmith@clarkparsia.com
