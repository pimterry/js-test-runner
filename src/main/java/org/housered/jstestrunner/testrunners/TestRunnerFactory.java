package org.housered.jstestrunner.testrunners;

import org.housered.jstestrunner.tests.TestPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestRunnerFactory
{
    
    private QUnitHtmlTestRunner qunitRunner;

    @Autowired
    public TestRunnerFactory(QUnitHtmlTestRunner qunitRunner) {
        this.qunitRunner = qunitRunner;
    }

    public TestRunner getRunnerForTestPage(TestPage test) {
        return qunitRunner;
    }

}
