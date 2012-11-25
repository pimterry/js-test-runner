package org.housered.jstestrunner.output;

import static java.util.Collections.*;
import static org.housered.jstestrunner.tests.TestSuiteResultBuilder.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.housered.jstestrunner.tests.TestResult;
import org.housered.jstestrunner.tests.TestSuiteResult;
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
        TestSuiteResult testSuiteResult = testSuiteResult("test suite", 5000)
                                                .withTestCase(new TestResult("t", 40, false))
                                                .withTestSuite(testSuiteResult("module 1", 500)
                                                        .withTestCase(new TestResult("test", 140, true))
                                                        .withTestCase(new TestResult("other test", 456, false)))
                                                .withTestSuite(testSuiteResult("module 2", 200)
                                                        .withTestSuite(testSuiteResult("contained suite", 1)
                                                                .withTestCase(new TestResult("test test", 1, true))))
                                                .build();

        outputter.writeTestSuiteToFile(testSuiteResult);
        outputter.finishTestOutput();
        
        assertElementMatchesResult(testSuiteResult, getElementFromOutput());
    }
    
    @Test
    public void shouldWriteOutputOfMultipleTests() throws Exception {
        TestSuiteResult result1 = new TestSuiteResult("tests", 3000, 14, 2, 4, 6, Collections.<TestResult> emptyList());        
        TestSuiteResult result2 = new TestSuiteResult("tests2", 3000, 7, 1, 2, 3, Collections.<TestResult> emptyList());        
        
        List<TestSuiteResult> results = Arrays.asList(result1, result2);
        
        for (TestSuiteResult result : results) {
        	outputter.writeTestSuiteToFile(result);
        }
        outputter.finishTestOutput();
        
        Element testSuitesElement = getElementFromOutput();
        assertEquals("testsuites", testSuitesElement.getName());

        for (int ii = 0; ii < testSuitesElement.getChildren().size(); ii++) {
        	Element testSuiteElement = testSuitesElement.getChildren().get(ii);
        	TestResult result = results.get(ii);
        	
        	assertElementMatchesResult(result, testSuiteElement);
        }
    }
    
    public Element getElementFromOutput() throws JDOMException, IOException {
    	InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new SAXBuilder().build(inputStream).getRootElement();
    }
    
    public void assertElementMatchesResult(TestSuiteResult result, Element element) throws DataConversionException {
        assertEquals("testsuite", element.getName());
        
        assertEquals(result.getName(), element.getAttribute("name").getValue());
        assertEquals(result.getTotalTestCount(), element.getAttribute("tests").getIntValue());
        assertEquals(result.getErrors(), element.getAttribute("errors").getIntValue());
        assertEquals(result.getFailures(), element.getAttribute("failures").getIntValue());
        assertEquals(result.getSkipped(), element.getAttribute("skip").getIntValue());
        assertEquals(result.getTotalTime(), element.getAttribute("time").getFloatValue() * 1000, 0.1);
        
        List<? extends TestResult> childResults = result.getTestResults();
        List<Element> childElements = element.getChildren();
        assertEquals(childResults.size(), childElements.size());
        
        for (int ii = 0; ii < childResults.size(); ii++) {
            assertElementMatchesResult(childResults.get(ii), childElements.get(ii));
        }
    }
    
    public void assertElementMatchesResult(TestResult result, Element element) throws DataConversionException {
        if (result instanceof TestSuiteResult) {
            assertElementMatchesResult((TestSuiteResult) result, element);
            return;
        }
        
        assertEquals("testcase", element.getName());
        assertEquals(result.getName(), element.getAttribute("name").getValue());
        assertEquals(result.getTestDurationMillis(), element.getAttribute("time").getFloatValue() * 1000, 0.1);
        
        if (!result.wasSuccess()) {
            List<Element> elementChildren = element.getChildren();
            
            assertEquals(1, elementChildren.size());
            assertEquals("failure", elementChildren.get(0).getName());
        }
    }

}
