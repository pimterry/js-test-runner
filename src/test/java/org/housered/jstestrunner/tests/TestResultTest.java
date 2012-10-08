package org.housered.jstestrunner.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.housered.jstestrunner.tests.TestResult.TestCaseResult;
import org.junit.Test;

public class TestResultTest
{

    @Test
    public void testConstructorAndGetters() {
        int tests = 10;
        int errors = 5;
        int failures = 2;
        int skipped = 1;
        int totalTime = 321;
        
        String name = "test-name";
        
        List<TestCaseResult> results = new ArrayList<TestCaseResult>();
        
        String testCaseName = "test-case-name";
        String testClassName = "test-class-name";
        int testDurationMillis = 123;
        boolean testCaseResult = true;
        results.add(new TestCaseResult(testClassName, testCaseName, testCaseResult, testDurationMillis));
        
        String testCaseName2 = "test-case-name-2";
        String testClassName2 = "test-class-name-2";
        int testDurationMillis2 = 456;
        boolean testCaseResult2 = false;
        results.add(new TestCaseResult(testClassName2, testCaseName2, testCaseResult2, testDurationMillis2));
        
        TestResult testResult = new TestResult(tests, failures, errors, skipped, totalTime, name, results);
        
        assertEquals(name, testResult.getName());
        assertEquals(tests, testResult.getTotalTestCount());
        assertEquals(errors, testResult.getErrors());
        assertEquals(failures, testResult.getFailures());
        assertEquals(skipped, testResult.getSkipped());
        assertEquals(totalTime, testResult.getTotalTime());
        
        TestCaseResult result1 = testResult.getTestResults().get(0);
        assertEquals(testClassName, result1.getTestClass());
        assertEquals(testCaseName, result1.getTestName());
        assertEquals(testCaseResult, result1.wasSuccess());
        assertEquals(testDurationMillis, result1.getTestDurationMillis());
        
        TestCaseResult result2 = testResult.getTestResults().get(1);
        assertEquals(testClassName2, result2.getTestClass());
        assertEquals(testCaseName2, result2.getTestName());
        assertEquals(testCaseResult2, result2.wasSuccess());
        assertEquals(testDurationMillis2, result2.getTestDurationMillis());
    }
    
}
