package org.housered.jstestrunner.tests;

import java.util.Collections;
import java.util.List;

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
        final int prime = 31;
        int result = 1;
        result = prime * result + errors;
        result = prime * result + failures;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + skipped;
        result = prime * result + ((testResults == null) ? 0 : testResults.hashCode());
        result = prime * result + tests;
        result = prime * result + totalTime;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        TestResult other = (TestResult) obj;
        
        if (errors != other.errors) return false;        
        if (failures != other.failures) return false;
        
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) {
            return false;
        }
    
        if (skipped != other.skipped) {
            return false;
        }
        
        if (testResults == null) {
            if (other.testResults != null) return false;
        } else if (!testResults.equals(other.testResults)) {
            return false;
        }
        
        if (tests != other.tests) return false;        
        if (totalTime != other.totalTime) return false;
        
        return true;
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
            final int prime = 31;
            int result = 1;
            result = prime * result + (success ? 1231 : 1237);
            result = prime * result + ((testClass == null) ? 0 : testClass.hashCode());
            result = prime * result + testDurationMillis;
            result = prime * result + ((testName == null) ? 0 : testName.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            
            TestCaseResult other = (TestCaseResult) obj;
            if (success != other.success) return false;
            
            if (testClass == null) {
                if (other.testClass != null) return false;
            } else if (!testClass.equals(other.testClass)) {
                return false;
            }
            
            if (testDurationMillis != other.testDurationMillis) {
                return false;
            }
            
            if (testName == null) {
                if (other.testName != null) return false;
            } else if (!testName.equals(other.testName)) {
                return false;
            }
            
            return true;
        }
        
        

    }

}
