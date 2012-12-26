Feature: Correctly finds and runs the tests specified

Scenario: Running a single test manually
  Given 'src/integration-tests/resources/discovery-test-folder/empty-qunit-1.html' [test] is a QUnit suite
  When a user runs 'js-test-runner --output target/single-test.xml [test]'
  Then 'target/single-test.xml' should describe 1 test suite passing

Scenario: Running a folder containing a selection of QUnit tests
	Given 'src/integration-tests/resources/discovery-test-folder' [discovery test folder] contains 2 QUnit tests
	When a user runs 'js-test-runner --output target/test-search.xml [discovery test folder]'
	Then 'target/test-search.xml' should describe 2 test suites passing
	
Scenario: Running a single file that contains no tests
  Given 'src/integration-tests/resources/discovery-test-folder/non-test-html.html' [file] is not a test suite
  When a user runs 'js-test-runner --output target/run-non-test.xml [file]'
  Then 'target/run-non-test.xml' should describe 0 test suites
  And the console output should contain 'No tests were found'