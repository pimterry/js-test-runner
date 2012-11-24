package org.housered.jstestrunner.testrunners;

import static org.housered.jstestrunner.tests.TestResultBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

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
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class QUnitHtmlTestRunnerTest
{

    @Mock
    private WebClient browser;

    private TestPage testPage;

    @InjectMocks
    private QUnitHtmlTestRunner testRunner;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        this.testPage = new SimpleHtmlTestPage(new File("test-page-path"));
        
    }

    @Test
    public void shouldParseTestResults() throws Exception
    {
        TestResult expectedResult = testResult("Test Title", 28)
                                                .withTestCase(new TestCaseResult("testClass", "test", true, 0))
                                                .withTestCase(new TestCaseResult("testClass", "test2", false, 0))
                                                .withTestCase(new TestCaseResult("testClass2", "final-test", true, 0))
                                                .build();
        
        HtmlPage resultsPage = asMockQUnitPage(expectedResult);
        when(browser.getPage(testPage.getFileURL())).thenReturn(resultsPage);

        TestResult actualResult = testRunner.runTest(testPage);

        assertEquals(expectedResult, actualResult);
    }
    
}
