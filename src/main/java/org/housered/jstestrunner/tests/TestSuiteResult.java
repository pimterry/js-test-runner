package org.housered.jstestrunner.tests;

import java.util.Collections;
import java.util.List;

public class TestSuiteResult extends TestResult {

    private final int tests;
    private final int failures;
    private final int errors;
    private final int skipped;
    private final int totalTime;

    private final List<? extends TestResult> testResults;

    public TestSuiteResult(String name, int totalTime, int tests, int failures, int errors, int skipped, List<? extends TestResult> testResults) {
        super(name, totalTime, failures == 0 && errors == 0);
        this.tests = tests;
        this.failures = failures;
        this.errors = errors;
        this.skipped = skipped;
        this.totalTime = totalTime;
        this.testResults = Collections.unmodifiableList(testResults);
    }
    
    public static TestSuiteResult newSuiteFromResultsList(String name, List<? extends TestResult> testResults) {
        int tests = 0;
        int failures = 0;
        int errors = 0;
        int skipped = 0;
        int totalTime = 0;
        
        for (TestResult result : testResults) {
            if (result instanceof TestSuiteResult) {
                TestSuiteResult suiteResult = (TestSuiteResult) result;
                tests += suiteResult.getTotalTestCount();
                failures += suiteResult.getFailures();
                errors += suiteResult.getErrors();
                skipped += suiteResult.getSkipped();
            } else {
                tests += 1;
                failures += result.wasSuccess() ? 0 : 1;
            }
            totalTime += result.getTestDurationMillis();
        }
        
        return new TestSuiteResult(name, totalTime, tests, failures, errors, skipped, testResults);
    }

    public int getTotalTestCount() {
        return tests;
    }

    public int getFailures() {
        return failures;
    }

    public int getErrors() {
        return errors;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public List<? extends TestResult> getTestResults() {
        return testResults;
    }

}
