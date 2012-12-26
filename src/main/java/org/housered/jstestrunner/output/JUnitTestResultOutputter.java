package org.housered.jstestrunner.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.housered.jstestrunner.tests.TestResult;
import org.housered.jstestrunner.tests.TestSuiteResult;
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

	public void writeTestSuiteToFile(TestSuiteResult result) {
		this.testSuiteElements.add(buildTestSuiteElement(result));
	}
	
	private Element buildTestSuiteElement(TestSuiteResult result) {
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

        for (TestResult testResult : result.getTestResults()) {
            if (testResult instanceof TestSuiteResult) {
                TestSuiteResult suiteResult = (TestSuiteResult) testResult;
                testSuite.addContent(buildTestSuiteElement(suiteResult));
            } else {
                testSuite.addContent(buildTestResultElement(testResult));
            }
        }
        
        return testSuite;
	}
	
    private Element buildTestResultElement(TestResult testResult) {
        Element testCase = new Element("testcase");
        
        testCase.setAttribute("name", testResult.getName());
        
        double testTime = testResult.getTestDurationMillis() / 1000d;
        if (testTime > 0) {
            testCase.setAttribute("time", String.valueOf(testResult.getTestDurationMillis() / 1000f));
        }

        if (!testResult.wasSuccess()) {
            testCase.addContent(new Element("failure"));
        }

        return testCase;
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
			Document noSuitesDoc = new Document(new Element("testsuites"));
			xmlOutputter.output(noSuitesDoc, outputStream);
		}
	}	

}
