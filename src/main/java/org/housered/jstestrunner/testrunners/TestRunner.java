package org.housered.jstestrunner.testrunners;

import org.housered.jstestrunner.tests.TestPage;
import org.housered.jstestrunner.tests.TestResult;

public interface TestRunner
{
    public TestResult runTest(TestPage test) throws UnableToRunTestException;
}
