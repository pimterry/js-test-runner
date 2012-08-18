package org.housered.jstestrunner.tests;

import java.util.Collections;
import java.util.List;

public class TestResult
{

    private final int tests;
    private final int failures;
    private final int errors;
    private final int skipped;

    private final String name;
    private final List<TestCaseResult> testResults;

    public TestResult(int tests, int failures, int errors, int skipped, String name, List<TestCaseResult> testResults)
    {
        this.tests = tests;
        this.failures = failures;
        this.errors = errors;
        this.skipped = skipped;
        this.name = name;
        this.testResults = Collections.unmodifiableList(testResults);
    }

    public int getTests()
    {
        return tests;
    }

    public int getFailures()
    {
        return failures;
    }

    public int getErrors()
    {
        return errors;
    }

    public int getSkipped()
    {
        return skipped;
    }

    public String getName()
    {
        return name;
    }

    public List<TestCaseResult> getTestResults()
    {
        return testResults;
    }

    public static class TestCaseResult
    {

        private int testDurationMillis;
        private String testClass;
        private String testName;

        public TestCaseResult(String testClass, String testName, int testDurationMillis)
        {
            this.testDurationMillis = testDurationMillis;
            this.testClass = testClass;
            this.testName = testName;
        }

        public int getTestDurationMillis()
        {
            return testDurationMillis;
        }

        public String getTestClass()
        {
            return testClass;
        }

        public String getTestName()
        {
            return testName;
        }

    }

}
