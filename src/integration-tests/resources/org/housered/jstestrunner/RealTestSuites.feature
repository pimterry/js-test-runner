Feature: Running existing popular test suites

Scenario: QUnit testing framework test suite passes when run by JS-Test-Runner
	Given 'src/integration-tests/resources/qunit-tests/index.html' [qunit suite] is the QUnit test suite
	When a user runs 'js-test-runner --output target/qunit-suite.xml [qunit suite]'
	Then 'target/qunit-suite.xml' should describe the qunit tests passing