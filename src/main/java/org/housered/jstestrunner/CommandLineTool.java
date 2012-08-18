package org.housered.jstestrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.housered.jstestrunner.output.TestResultOutputter;
import org.housered.jstestrunner.output.TestResultOutputterFactory;
import org.housered.jstestrunner.testrunners.TestRunner;
import org.housered.jstestrunner.testrunners.TestRunnerFactory;
import org.housered.jstestrunner.tests.TestPage;
import org.housered.jstestrunner.tests.TestPageFactory;
import org.housered.jstestrunner.tests.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

@Component
public class CommandLineTool
{

    @Parameter(description = "List of test suite files to run")
    private List<String> tests = new ArrayList<String>();

    private TestPageFactory testPageFactory;
    private TestRunnerFactory testRunnerFactory;
    private TestResultOutputterFactory testOutputterFactory;
    private JCommander jCommander;

    @Autowired
    public CommandLineTool(JCommander jCommander, TestPageFactory testPageFactory)
    {
        this.jCommander = jCommander;
        this.testPageFactory = testPageFactory;
    }

    public void run(String[] args)
    {
        jCommander.parse(args);

        for (String test : tests)
        {
            try 
            {
                runTest(test);
            }
            catch (IOException e) {
                System.err.println("Failed to record test results for test " + test);
            }
        }

        System.out.println("Done");
    }

    private void runTest(String testName) throws IOException
    {
        String outputFile = "output.xml";        
        
        // Build test inputs
        TestPage test = testPageFactory.getTestPage(testName);
        
        // Get test runner
        TestRunner testRunner = testRunnerFactory.getRunnerForTestPage(test);
        
        // Run tests
        TestResult result = testRunner.runTest(test);
        
        // Build test output
        TestResultOutputter outputter = testOutputterFactory.getTestResultOutputter();
        outputter.writeTestResultToFile(result, outputFile);
    }

}
