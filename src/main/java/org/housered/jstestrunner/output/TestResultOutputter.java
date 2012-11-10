package org.housered.jstestrunner.output;

import java.io.IOException;
import java.io.OutputStream;

import org.housered.jstestrunner.tests.TestResult;

public interface TestResultOutputter
{

    void writeTestResultToFile(TestResult result) throws IOException;
    
}
