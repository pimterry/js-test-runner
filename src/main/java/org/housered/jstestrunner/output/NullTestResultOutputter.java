package org.housered.jstestrunner.output;

import java.io.IOException;

import org.housered.jstestrunner.tests.TestResult;

/**
 * Test result outputter purely for testing: doesn't actually output the results anywhere.
 * @author Tim
 */
public class NullTestResultOutputter implements TestResultOutputter
{

    public void writeTestResultToFile(TestResult result, String outputFile) throws IOException
    {
        return;
    }

}
