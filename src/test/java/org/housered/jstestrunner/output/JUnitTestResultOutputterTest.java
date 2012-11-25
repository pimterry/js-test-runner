package org.housered.jstestrunner.output;

import static java.util.Collections.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.housered.jstestrunner.tests.TestResult;
import org.housered.jstestrunner.tests.TestResult.TestCaseResult;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JUnitTestResultOutputterTest
{

    private ByteArrayOutputStream outputStream;
    private JUnitTestResultOutputter outputter;

    @Before
    public void setup() {
        outputStream = new ByteArrayOutputStream();
        outputter = new JUnitTestResultOutputter(outputStream);
    }

    @Test
    public void shouldWriteOutputOfTestResult() throws Exception {
        List<TestCaseResult> testCaseResults = new ArrayList<TestCaseResult>();
        testCaseResults.add(new TestCaseResult("testClass", "testName", true, 123));
        testCaseResults.add(new TestCaseResult("testClass2", "testName2", false, 456));

        TestResult result = new TestResult(10, 5, 2, 1, 5000, "test suite", testCaseResults);

        outputter.writeTestResultToFile(result);
        outputter.finishTestOutput();
        
        Element testSuiteElement = getElementFromOutput();        
        List<Element> testCaseElements = testSuiteElement.getChildren();
        
        assertSuiteElementMatches(result, testSuiteElement);

        for (int ii = 0; ii < testCaseElements.size(); ii++) {
        	assertCaseElementMatches(result.getTestResults().get(ii), testCaseElements.get(ii));
        }
    }
    
    @Test
    public void shouldWriteOutputOfTestsWithNullClassNames() throws Exception {
        TestResult result = new TestResult(10, 5, 2, 1, 5000, "test suite", singletonList(new TestCaseResult(null, "testName", true, 111)));

        outputter.writeTestResultToFile(result);
        outputter.finishTestOutput();
        
        Element testSuiteElement = getElementFromOutput();        
        List<Element> testCaseElements = testSuiteElement.getChildren();
        
        assertSuiteElementMatches(result, testSuiteElement);

        for (int ii = 0; ii < testCaseElements.size(); ii++) {
            assertCaseElementMatches(result.getTestResults().get(ii), testCaseElements.get(ii));
        }
    }
    
    @Test
    public void shouldWriteOutputOfMultipleTests() throws Exception {
        TestResult result2 = new TestResult(14, 2, 4, 6, 3000, "tests", Collections.<TestCaseResult> emptyList());
        TestResult result1 = new TestResult(7, 1, 2, 3, 3000, "tests2", Collections.<TestCaseResult> emptyList());
        
        List<TestResult> results = Arrays.asList(result1, result2);
        
        for (TestResult result : results) {
        	outputter.writeTestResultToFile(result);
        }
        outputter.finishTestOutput();
        
        Element testSuitesElement = getElementFromOutput();

        for (int ii = 0; ii < testSuitesElement.getChildren().size(); ii++) {
        	Element testSuite = testSuitesElement.getChildren().get(ii);
        	TestResult result = results.get(ii);
        	
        	assertSuiteElementMatches(result, testSuite);
        }
    }
    
    public Element getElementFromOutput() throws JDOMException, IOException {
    	InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new SAXBuilder().build(inputStream).getRootElement();
    }
    
    public void assertSuiteElementMatches(TestResult result, Element element) throws DataConversionException {
        assertEquals(result.getName(), element.getAttribute("name").getValue());
        assertEquals(result.getTotalTestCount(), element.getAttribute("tests").getIntValue());
        assertEquals(result.getErrors(), element.getAttribute("errors").getIntValue());
        assertEquals(result.getFailures(), element.getAttribute("failures").getIntValue());
        assertEquals(result.getSkipped(), element.getAttribute("skip").getIntValue());
        assertEquals(result.getTotalTime(), element.getAttribute("time").getFloatValue() * 1000, 0.1);
    }
    
    public void assertCaseElementMatches(TestCaseResult result, Element element) throws DataConversionException {
        if (element.getAttribute("classname") == null) {
            assertNull(result.getTestClass());
        } else {
            assertEquals(result.getTestClass(), element.getAttribute("classname").getValue());
        }
        assertEquals(result.getTestName(), element.getAttribute("name").getValue());
        assertEquals(result.getTestDurationMillis(), element.getAttribute("time").getFloatValue() * 1000, 0.1);
    }

}
