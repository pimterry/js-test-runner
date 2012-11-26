Feature: Running the QUnit Test Suite

Scenario: QUnit test suite passes when run by JS-Test-Runner
	Given the QUnit test suite is at 'src/integration-tests/resources/qunit-tests/index.html'
	When a user runs 'js-test-runner --output qunit-output.xml src/integration-tests/resources/qunit-tests/index.html'
	Then './qunit-output.xml' should describe the qunit tests passing