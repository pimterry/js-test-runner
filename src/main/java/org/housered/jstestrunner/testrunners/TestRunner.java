package org.housered.jstestrunner.testrunners;

import org.housered.jstestrunner.tests.TestPage;
import org.housered.jstestrunner.tests.TestSuiteResult;

public interface TestRunner
{
    public TestSuiteResult runTest(TestPage test) throws UnableToRunTestException;
}
