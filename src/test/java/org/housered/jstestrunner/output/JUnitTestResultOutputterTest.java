package org.housered.jstestrunner.output;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.housered.jstestrunner.tests.TestResult;
import org.housered.jstestrunner.tests.TestResult.TestCaseResult;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JUnitTestResultOutputterTest
{
    
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    @InjectMocks
    private JUnitTestResultOutputter outputter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldWriteBasicOutputAsValidXML() throws Exception {
        int totalTests = 10;
        int failedTests = 3;
        int errorTests = 2;
        int skippedTests = 1;
        String name = "test name";
        
        TestCaseResult testCaseResult1 = new TestCaseResult("testClass", "testName", true, 123);
        TestCaseResult testCaseResult2 = new TestCaseResult("testClass2", "testName2", false, 456);
        
        List<TestCaseResult> testCaseResults = new ArrayList<TestCaseResult>();
        testCaseResults.add(testCaseResult1);
        testCaseResults.add(testCaseResult2);
        
        TestResult result = new TestResult(totalTests, failedTests, errorTests, skippedTests, 1, name, testCaseResults);
        
        outputter.writeTestResultToFile(result, outputStream);
        
        Element testSuiteNode = new SAXBuilder().build(new ByteArrayInputStream(outputStream.toByteArray())).getRootElement();
        
        assertEquals(name, testSuiteNode.getAttribute("name").getValue());
        assertEquals(totalTests, testSuiteNode.getAttribute("tests").getIntValue());
        assertEquals(errorTests, testSuiteNode.getAttribute("errors").getIntValue());
        assertEquals(failedTests, testSuiteNode.getAttribute("failures").getIntValue());
        assertEquals(skippedTests, testSuiteNode.getAttribute("skip").getIntValue());
        
        List<Element> testCaseNodes = testSuiteNode.getChildren();
        
        assertEquals(2, testCaseNodes.size());
        
        Element testCaseNode1 = testCaseNodes.get(0);
        assertEquals(testCaseResult1.getTestClass(), testCaseNode1.getAttribute("classname").getValue());
        assertEquals(testCaseResult1.getTestName(), testCaseNode1.getAttribute("name").getValue());
        assertEquals(testCaseResult1.getTestDurationMillis(), testCaseNode1.getAttribute("time").getIntValue());
        
        Element testCaseNode2 = testCaseNodes.get(1);
        assertEquals(testCaseResult2.getTestClass(), testCaseNode2.getAttribute("classname").getValue());
        assertEquals(testCaseResult2.getTestName(), testCaseNode2.getAttribute("name").getValue());
        assertEquals(testCaseResult2.getTestDurationMillis(), testCaseNode2.getAttribute("time").getIntValue());        
    }
    
}
