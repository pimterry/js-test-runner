package org.housered.jstestrunner.testrunners;

import java.util.Collections;

import org.housered.jstestrunner.tests.TestPage;
import org.housered.jstestrunner.tests.TestResult;
import org.housered.jstestrunner.tests.TestResult.TestCaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomChangeEvent;
import com.gargoylesoftware.htmlunit.html.DomChangeListener;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Component
public class QUnitTestRunner implements TestRunner
{

    private WebClient browser;
    
    private static final String RESULTS_SUMMARY_XPATH = "//*[@id=\"qunit-testresult\"]";

    @Autowired
    public QUnitTestRunner(WebClient browser)
    {
        this.browser = browser;
    }

    public TestResult runTest(TestPage test) throws UnableToRunTestException
    {
        HtmlPage page;

        try
        {
            page = browser.getPage(test.getFileURL());
        }
        catch (Exception e)
        {
            throw new UnableToRunTestException(e);
        }
        
        waitUntilResultsAreReadyOn(page);
        
        return getTestResultFrom(page);
    }

    private TestResult getTestResultFrom(HtmlPage resultsPage)
    {
        DomNode resultsNode = resultsPage.getFirstByXPath(RESULTS_SUMMARY_XPATH);
        
        DomNode totalTestsNode = resultsNode.getFirstByXPath("//*[@class=\"total\"]");
        int totalTests = Integer.parseInt(totalTestsNode.getTextContent());
        
        DomNode failedTestsNode = resultsNode.getFirstByXPath("//*[@class=\"failed\"]");
        int failedTests = Integer.parseInt(failedTestsNode.getTextContent());
        
        return new TestResult(totalTests, failedTests, failedTests, 0, "QUnit Test Suite", Collections.<TestCaseResult> emptyList());
    }

    private void waitUntilResultsAreReadyOn(HtmlPage resultsPage)
    {
        DomNode resultsNode = resultsPage.getFirstByXPath(RESULTS_SUMMARY_XPATH);
        ElementListenerAndNotifier resultsListener = new ElementListenerAndNotifier();
        
        synchronized(resultsListener) {
            resultsNode.addDomChangeListener(resultsListener);
    
            while (!resultsReady(resultsNode))
            {
                try
                {
                    resultsListener.wait();
                }
                catch (InterruptedException e) { }
            }
        }
    }

    private boolean resultsReady(DomNode resultsNode)
    {
        return resultsNode.asText().contains("Tests completed");
    }

    private class ElementListenerAndNotifier implements DomChangeListener
    {
        private static final long serialVersionUID = 5876321513155410935L;

        public void nodeAdded(DomChangeEvent event)
        {
            this.notifyAll();
        }

        public void nodeDeleted(DomChangeEvent event) { }

    }

}
