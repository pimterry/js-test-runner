package org.housered.jstestrunner.tests;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.housered.jstestrunner.tests.TestResult.TestCaseResult;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestResultBuilder {

    private String name;
    private int failures;
    private int millisTaken;    
    private final List<TestCaseResult> testCases;    

    private TestResultBuilder(String name, int millisTaken) {
        testCases = new ArrayList<TestCaseResult>();
        this.name = name;
        this.millisTaken = millisTaken;
    }
    
    public TestResultBuilder withTestCase(TestCaseResult testCase) {
        this.testCases.add(testCase);
        
        if (!testCase.wasSuccess()) {
            failures += 1;
        }
        
        return this;
    }
    
    public TestResult build() {
        return new TestResult(testCases.size(), failures, 0, 0, millisTaken, name, testCases);
    }
    
    public static HtmlPage asMockQUnitPage(TestResult testResult) {
        HtmlPage resultsPage = mock(HtmlPage.class);
        
        List<DomElement> testResultElements = new ArrayList<DomElement>();
        
        for (TestCaseResult result : testResult.getTestResults()) {
            testResultElements.add(mockTestCaseElement(result));
        }
        
        DomElement resultsElement = mock(DomElement.class);        
        when(resultsPage.getFirstByXPath(contains("testresult"))).thenReturn(resultsElement);
        doReturn(testResultElements).when(resultsElement).getByXPath(contains("qunit-test-output"));
        
        DomElement titleElement = mock(DomElement.class);
        when(resultsPage.getFirstByXPath(contains("qunit-header"))).thenReturn(titleElement);
        when(titleElement.getTextContent()).thenReturn(testResult.getName());       
        
        int totalTests = testResult.getTotalTestCount();
        int failedTests = testResult.getFailures();
        int passedTests = totalTests - failedTests;
        
        when(resultsElement.getTextContent()).thenReturn(
                "Tests completed in " + testResult.getTotalTime() + " milliseconds.\n" + 
                passedTests + " tests of " + totalTests + " passed, " + failedTests + " failed.");
        
        DomElement totalNode = mock(DomElement.class);
        when(resultsElement.getFirstByXPath(contains("total"))).thenReturn(totalNode);
        when(totalNode.getTextContent()).thenReturn(String.valueOf(totalTests));

        DomElement failedNode = mock(DomElement.class);
        when(resultsElement.getFirstByXPath(contains("failed"))).thenReturn(failedNode);
        when(failedNode.getTextContent()).thenReturn(String.valueOf(failedTests));        
        
        return resultsPage;
    }
    
    private static DomElement mockTestCaseElement(TestCaseResult result) {
        DomElement testCase = mock(DomElement.class);
        
        if (result.wasSuccess()) when(testCase.getAttribute("class")).thenReturn("pass");
        else when(testCase.getAttribute("class")).thenReturn("fail");

        if (result.getTestClass() != null) {
            DomElement testClassElement = mock(DomElement.class);
            when(testClassElement.getTextContent()).thenReturn(result.getTestClass());
            when(testCase.getFirstByXPath(contains("module-name"))).thenReturn(testClassElement);
        }

        DomElement testNameElement = mock(DomElement.class);
        when(testNameElement.getTextContent()).thenReturn(result.getTestName());
        when(testCase.getFirstByXPath(contains("test-name"))).thenReturn(testNameElement);

        return testCase;
    }

    public static TestResultBuilder testResult(String name, int millisTaken) {
        return new TestResultBuilder(name, millisTaken);
    }    
    
}
