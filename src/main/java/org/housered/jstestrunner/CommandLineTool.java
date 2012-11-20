package org.housered.jstestrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.housered.jstestrunner.output.TestResultOutputter;
import org.housered.jstestrunner.output.TestResultOutputterFactory;
import org.housered.jstestrunner.testrunners.TestRunner;
import org.housered.jstestrunner.testrunners.TestRunnerFactory;
import org.housered.jstestrunner.testrunners.UnableToRunTestException;
import org.housered.jstestrunner.tests.TestPage;
import org.housered.jstestrunner.tests.TestPageFactory;
import org.housered.jstestrunner.tests.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

@Component
public class CommandLineTool {

	private static final Logger LOG = LoggerFactory.getLogger(CommandLineTool.class);

	@Parameter(description = "List of test suite files or folders to run")
	private List<String> tests = new ArrayList<String>();

	@Parameter(names = { "-o", "--output" }, description = "Path to output the test results to", required = false)
	private String outputPath = "./test-results.xml";

	private final TestPageFactory testPageFactory;
	private final TestRunnerFactory testRunnerFactory;
	private final TestResultOutputterFactory testOutputterFactory;
	private final JCommander jCommander;

	@Autowired
	public CommandLineTool(JCommander jCommander, TestPageFactory testPageFactory, TestRunnerFactory testRunnerFactory, TestResultOutputterFactory testOutputterFactory) {
		this.jCommander = jCommander;
		this.testPageFactory = testPageFactory;
		this.testRunnerFactory = testRunnerFactory;
		this.testOutputterFactory = testOutputterFactory;

		jCommander.addObject(this);
	}

	public void run(String[] args) {
		jCommander.parse(args);

		if (tests.isEmpty()) {
			LOG.error("No tests specified");
			jCommander.usage();
			return;
		}

		LOG.info("Test run starting");

		TestResultOutputter testOutputter = null;
		try {
			testOutputter = testOutputterFactory.getTestResultOutputter(outputPath);
		} catch (IOException e) {
			LOG.error("Unable to initialise test outputter", e);
			return;
		}

		for (String test : tests) {
			try {
				runTest(test, testOutputter);
			} catch (UnableToRunTestException e) {
				LOG.error("Failed to run test {}", test, e);
			} catch (IOException e) {
				LOG.error("Failed to record test results for test {}", test, e);
			}
		}
		
		try {
			testOutputter.finishTestOutput();
		} catch (IOException e) {
			LOG.error("Unable to finish test output", e);
			return;
		}
		LOG.info("Test run completed");
	}

	private void runTest(String testName, TestResultOutputter testOutputter) throws IOException, UnableToRunTestException {
		List<TestPage> testPages = testPageFactory.getTestPages(testName);
		
		for (TestPage test : testPages) {
			TestRunner testRunner = testRunnerFactory.getRunnerForTestPage(test);
			TestResult result = testRunner.runTest(test);
	
			testOutputter.writeTestResultToFile(result);
		}
	}

}
