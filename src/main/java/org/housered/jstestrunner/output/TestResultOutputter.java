package org.housered.jstestrunner.output;

import java.io.IOException;

public interface TestResultOutputter
{

    public void writeOutput(String outputFile) throws IOException;
    
}
