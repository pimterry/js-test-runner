package org.housered.jstestrunner.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.housered.jstestrunner.tests.TestResult;
import org.housered.jstestrunner.tests.TestResult.TestCaseResult;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JUnitTestResultOutputter implements TestResultOutputter {

	private OutputStream outputStream;
	private ArrayList<Element> testSuiteElements;

	public JUnitTestResultOutputter(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.testSuiteElements = new ArrayList<Element>();
	}

	public void writeTestResultToFile(TestResult result) {
		Element testSuite = new Element("testsuite");

		testSuite.setAttribute("name", result.getName());
		testSuite.setAttribute("tests", String.valueOf(result.getTotalTestCount()));
		testSuite.setAttribute("errors", String.valueOf(result.getErrors()));
		testSuite.setAttribute("failures", String.valueOf(result.getFailures()));
		testSuite.setAttribute("skip", String.valueOf(result.getSkipped()));

		double totalTime = result.getTotalTime() / 1000d;
		if (totalTime > 0) {
			testSuite.setAttribute("time", String.valueOf(totalTime));
		}

		for (TestCaseResult testCaseResult : result.getTestResults()) {
			Element testCase = new Element("testcase");
			if (testCaseResult.getTestClass() != null) {
			    testCase.setAttribute("classname", testCaseResult.getTestClass());
			}
			testCase.setAttribute("name", testCaseResult.getTestName());

			double testTime = testCaseResult.getTestDurationMillis() / 1000d;

			if (testTime > 0) {
				testCase.setAttribute("time", String.valueOf(testCaseResult
						.getTestDurationMillis() / 1000f));
			}

			if (!testCaseResult.wasSuccess()) {
				// TODO make status an enum
				testCase.addContent(new Element("failure"));
			}

			// TODO support individual test case results + messages
			testSuite.addContent(testCase);
		}
		
		this.testSuiteElements.add(testSuite);
	}
	
	public void finishTestOutput() throws IOException {
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		
		if (testSuiteElements.size() > 1) {
			Element testSuitesElement = new Element("testsuites");
	        Document testSuitesDoc = new Document(testSuitesElement);
			
			for (Element suite : testSuiteElements) {
				testSuitesElement.addContent(suite);
			}
			
			xmlOutputter.output(testSuitesDoc, outputStream);
		} else if (testSuiteElements.size() == 1) {
			xmlOutputter.output(new Document(testSuiteElements.get(0)), outputStream);
		} else {
			return;
		}
	}	

}
