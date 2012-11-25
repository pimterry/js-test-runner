package org.housered.jstestrunner.output;

import java.io.IOException;

import org.housered.jstestrunner.tests.TestSuiteResult;

public interface TestResultOutputter
{
    void writeTestSuiteToFile(TestSuiteResult result) throws IOException;

	void finishTestOutput() throws IOException;
    
}
