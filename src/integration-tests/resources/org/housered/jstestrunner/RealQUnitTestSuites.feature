Feature: Running existing popular QUnit test suites

Scenario: QUnit testing framework test suite passes when run by JS-Test-Runner
	Given the QUnit test suite is at 'src/integration-tests/resources/qunit-tests/index.html' [qunit suite]
	When a user runs 'js-test-runner --output qunit-suite-output.xml [qunit suite]'
	Then './qunit-suite-output.xml' should describe the qunit tests passing