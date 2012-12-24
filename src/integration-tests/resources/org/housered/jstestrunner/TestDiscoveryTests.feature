Feature: Correctly finds and runs tests by searching a path

Scenario: Running a folder containing a selection of QUnit tests
	Given 'src/integration-tests/resources/discovery-test-folder' [discovery test folder] contains 2 QUnit tests
	When a user runs 'js-test-runner --output discovery-output.xml [discovery test folder]'
	Then './discovery-output.xml' should describe 2 test suites passing