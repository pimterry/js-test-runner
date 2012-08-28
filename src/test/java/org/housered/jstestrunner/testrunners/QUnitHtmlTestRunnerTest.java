package org.housered.jstestrunner.testrunners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.housered.jstestrunner.tests.SimpleHtmlTestPage;
import org.housered.jstestrunner.tests.TestPage;
import org.housered.jstestrunner.tests.TestResult;
import org.housered.jstestrunner.tests.TestResult.TestCaseResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class QUnitHtmlTestRunnerTest
{

    @Mock
    private WebClient browser;

    @Mock
    private HtmlPage resultsPage;

    @Mock
    private DomElement resultsElement;

    private TestPage testPage;

    @InjectMocks
    private QUnitHtmlTestRunner testRunner;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        this.testPage = new SimpleHtmlTestPage("test-page-path");
        when(browser.getPage(testPage.getFilePath())).thenReturn(resultsPage);
        when(resultsPage.getFirstByXPath(contains("testresult"))).thenReturn(resultsElement);
    }

    @Test
    public void shouldUnderstandTestSummary() throws Exception
    {
        int testMillisTaken = 28;
        int totalTests = 3;
        int failedTests = 1;

        when(resultsElement.getTextContent()).thenReturn(
                "Tests completed in " + testMillisTaken + " milliseconds.\n2 tests of 3 passed, 1 failed.");

        DomElement totalNode = mock(DomElement.class);
        when(resultsElement.getFirstByXPath(contains("total"))).thenReturn(totalNode);
        when(totalNode.getTextContent()).thenReturn(String.valueOf(totalTests));

        DomElement failedNode = mock(DomElement.class);
        when(resultsElement.getFirstByXPath(contains("failed"))).thenReturn(failedNode);
        when(failedNode.getTextContent()).thenReturn(String.valueOf(failedTests));

        List<DomElement> testCases = Arrays.asList(mockTestCaseElement("testClass", "test1", true),
                mockTestCaseElement("testClass", "test2", false), mockTestCaseElement("testClass2", "test3", true));
        doReturn(testCases).when(resultsElement).getByXPath(contains("qunit-test-output"));

        TestResult testResult = testRunner.runTest(testPage);

        assertEquals(totalTests, testResult.getTotalTestCount());
        assertEquals(failedTests, testResult.getFailures());
        assertEquals(0, testResult.getErrors());
        assertEquals(0, testResult.getSkipped());
        assertEquals(testMillisTaken, testResult.getTotalTime());

        List<TestCaseResult> testCaseResults = testResult.getTestResults();
        assertEquals(totalTests, testCaseResults.size());

        TestCaseResult test1 = testCaseResults.get(0);
        TestCaseResult test2 = testCaseResults.get(1);
        TestCaseResult test3 = testCaseResults.get(2);

        assertEquals("testClass", test1.getTestClass());
        assertEquals("testClass", test2.getTestClass());
        assertEquals("testClass2", test3.getTestClass());

        assertEquals("test1", test1.getTestName());
        assertEquals("test2", test2.getTestName());
        assertEquals("test3", test3.getTestName());

        assertTrue(test1.wasSuccess());
        assertFalse(test2.wasSuccess());
        assertTrue(test3.wasSuccess());
    }

    private DomElement mockTestCaseElement(String className, String testName, boolean success)
    {
        DomElement testCase = mock(DomElement.class);
        
        if (success) when(testCase.getAttribute("class")).thenReturn("pass");
        else when(testCase.getAttribute("class")).thenReturn("fail");

        DomElement testClassElement = mock(DomElement.class);
        when(testClassElement.getTextContent()).thenReturn(className);
        when(testCase.getFirstByXPath(contains("module-name"))).thenReturn(testClassElement);

        DomElement testNameElement = mock(DomElement.class);
        when(testNameElement.getTextContent()).thenReturn(testName);
        when(testCase.getFirstByXPath(contains("test-name"))).thenReturn(testNameElement);

        return testCase;
    }
}
