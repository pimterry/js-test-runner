package org.housered.jstestrunner.output;

import java.io.IOException;

import org.housered.jstestrunner.tests.TestResult;

public interface TestResultOutputter
{

    public void writeTestResultToFile(TestResult result, String outputFile) throws IOException;
    
}
