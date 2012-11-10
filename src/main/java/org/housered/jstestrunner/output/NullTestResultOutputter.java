package org.housered.jstestrunner.output;

import java.io.IOException;
import java.io.OutputStream;

import org.housered.jstestrunner.tests.TestResult;

/**
 * Test result outputter purely for testing: doesn't actually output the results anywhere.
 * @author Tim
 */
public class NullTestResultOutputter implements TestResultOutputter
{

    public void writeTestResultToFile(TestResult result) throws IOException
    {
        return;
    }

}
