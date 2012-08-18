package org.housered.jstestrunner.testrunners;

import org.housered.jstestrunner.tests.TestPage;

public interface TestRunnerFactory
{

    public TestRunner getRunnerForTestPage(TestPage test);

}
