package org.housered.jstestrunner.output;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldWriteBasicOutputAsValidXML() throws Exception
    {
        TestCaseResult testCaseResult1 = new TestCaseResult("testClass", "testName", true, 123);
        TestCaseResult testCaseResult2 = new TestCaseResult("testClass2", "testName2", false, 456);

        List<TestCaseResult> testCaseResults = new ArrayList<TestCaseResult>();
        testCaseResults.add(testCaseResult1);
        testCaseResults.add(testCaseResult2);

        TestResult result = new TestResult(10, 5, 2, 1, 5000, "test suite", testCaseResults);

        outputter.writeTestResultToFile(result, outputStream);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        Element testSuiteNode = new SAXBuilder().build(inputStream).getRootElement();

        assertEquals(result.getName(), testSuiteNode.getAttribute("name").getValue());
        assertEquals(result.getTotalTestCount(), testSuiteNode.getAttribute("tests").getIntValue());
        assertEquals(result.getErrors(), testSuiteNode.getAttribute("errors").getIntValue());
        assertEquals(result.getFailures(), testSuiteNode.getAttribute("failures").getIntValue());
        assertEquals(result.getSkipped(), testSuiteNode.getAttribute("skip").getIntValue());
        assertEquals(result.getTotalTime(), testSuiteNode.getAttribute("time").getFloatValue() * 1000, 0.1);

        List<Element> testCaseNodes = testSuiteNode.getChildren();

        assertEquals(2, testCaseNodes.size());

        Element testCaseNode1 = testCaseNodes.get(0);
        assertEquals(testCaseResult1.getTestClass(), testCaseNode1.getAttribute("classname").getValue());
        assertEquals(testCaseResult1.getTestName(), testCaseNode1.getAttribute("name").getValue());
        assertEquals(testCaseResult1.getTestDurationMillis(), testCaseNode1.getAttribute("time").getFloatValue() * 1000, 0.1);

        Element testCaseNode2 = testCaseNodes.get(1);
        assertEquals(testCaseResult2.getTestClass(), testCaseNode2.getAttribute("classname").getValue());
        assertEquals(testCaseResult2.getTestName(), testCaseNode2.getAttribute("name").getValue());
        assertEquals(testCaseResult2.getTestDurationMillis(), testCaseNode2.getAttribute("time").getFloatValue() * 1000, 0.1);
    }

}
