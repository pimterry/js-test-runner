package org.housered.jstestrunner.tests;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TestResult
{

    private final int tests;
    private final int failures;
    private final int errors;
    private final int skipped;
    private final int totalTime;

    private final String name;
    private final List<TestCaseResult> testResults;

    public TestResult(int tests, int failures, int errors, int skipped, int totalTime, String name,
            List<TestCaseResult> testResults)
    {
        this.tests = tests;
        this.failures = failures;
        this.errors = errors;
        this.skipped = skipped;
        this.totalTime = totalTime;
        this.name = name;
        this.testResults = Collections.unmodifiableList(testResults);
    }

    public int getTotalTestCount()
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

    public int getTotalTime()
    {
        return totalTime;
    }

    public String getName()
    {
        return name;
    }

    public List<TestCaseResult> getTestResults()
    {
        return testResults;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public static class TestCaseResult
    {

        private boolean success;
        private int testDurationMillis;
        private String testClass;
        private String testName;

        public TestCaseResult(String testClass, String testName, boolean success, int testDurationMillis)
        {
            this.testDurationMillis = testDurationMillis;
            this.testClass = testClass;
            this.testName = testName;
            this.success = success;
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

        public boolean wasSuccess()
        {
            return success;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

    }

}
