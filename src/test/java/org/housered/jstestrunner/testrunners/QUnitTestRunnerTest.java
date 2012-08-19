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
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class QUnitTestRunnerTest
{

    @Mock
    private WebClient browser;

    @Mock
    private HtmlPage resultsPage;

    @Mock
    private DomNode resultsNode;

    private TestPage testPage;

    @InjectMocks
    private QUnitTestRunner testRunner;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        this.testPage = new SimpleHtmlTestPage("test-page-path");
        when(browser.getPage(testPage.getFileURL())).thenReturn(resultsPage);
        when(resultsPage.getFirstByXPath(contains("testresult"))).thenReturn(resultsNode);
    }

    @Test
    public void shouldUnderstandTestSummary() throws Exception {
        when(resultsNode.asText()).thenReturn("Tests completed in 28 milliseconds.\n3 tests of 5 passed, 2 failed.");
        
        DomNode totalNode = mock(DomNode.class);
        when(resultsNode.getFirstByXPath(contains("total"))).thenReturn(totalNode);
        when(totalNode.getTextContent()).thenReturn("5");
        
        DomNode failedNode = mock(DomNode.class);
        when(resultsNode.getFirstByXPath(contains("failed"))).thenReturn(failedNode);
        when(failedNode.getTextContent()).thenReturn("2");
        
        TestResult result = testRunner.runTest(testPage);
        
        assertEquals(5, result.getTests());
        assertEquals(2, result.getFailures());
        assertEquals(0, result.getSkipped());
    }
}
