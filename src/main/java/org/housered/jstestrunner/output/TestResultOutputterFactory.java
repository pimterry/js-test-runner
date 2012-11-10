package org.housered.jstestrunner.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class TestResultOutputterFactory
{

    public TestResultOutputter getTestResultOutputter(String outputPath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File(outputPath));    	
        return new JUnitTestResultOutputter(outputStream);
    }

}
