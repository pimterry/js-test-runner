package org.housered.jstestrunner.testrunners;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.housered.jstestrunner.tests.SimpleHtmlTestPage;
import org.housered.jstestrunner.tests.TestPage;
import org.housered.jstestrunner.tests.TestResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class QUnitTestRunnerTest
{

    @Mock
    private WebClient browser;

    @Mock
    private HtmlPage resultsPage;

    @Mock
    private DomElement resultsElement;

    private TestPage testPage;

    @InjectMocks
    private QUnitTestRunner testRunner;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        this.testPage = new SimpleHtmlTestPage("test-page-path");
        when(browser.getPage(testPage.getFileURL())).thenReturn(resultsPage);
        when(resultsPage.getFirstByXPath(contains("testresult"))).thenReturn(resultsElement);
    }

    @Test
    public void shouldUnderstandTestSummary() throws Exception {
        int testMillisTaken = 28;
        int totalTests = 5;
        int failedTests = 2;
        
        when(resultsElement.getTextContent()).thenReturn("Tests completed in " + testMillisTaken + 
                                                         " milliseconds.\n3 tests of 5 passed, 2 failed.");
        
        DomElement totalNode = mock(DomElement.class);
        when(resultsElement.getFirstByXPath(contains("total"))).thenReturn(totalNode);
        when(totalNode.getTextContent()).thenReturn(String.valueOf(totalTests));
        
        DomElement failedNode = mock(DomElement.class);
        when(resultsElement.getFirstByXPath(contains("failed"))).thenReturn(failedNode);
        when(failedNode.getTextContent()).thenReturn(String.valueOf(failedTests));
        
        TestResult result = testRunner.runTest(testPage);
        
        assertEquals(totalTests, result.getTotalTestCount());
        assertEquals(failedTests, result.getFailures());
        assertEquals(0, result.getErrors());
        assertEquals(0, result.getSkipped());
        assertEquals(testMillisTaken, result.getTotalTime());
    }
}
