package org.housered.jstestrunner.tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    
    @Test
    public void emptyTestResultsShouldBeEqual() {
        TestResult testResult1 = new TestResult(0, 0, 0, 0, 10, "empty test", Collections.<TestCaseResult> emptyList());
        TestResult testResult2 = new TestResult(0, 0, 0, 0, 10, "empty test", Collections.<TestCaseResult> emptyList());
        
        assertEquals(testResult1, testResult2);
        assertEquals(testResult1.hashCode(), testResult2.hashCode());
    }
    
    @Test
    public void sameTestResultsShouldBeEqualWithEqualHashCodes() {
        TestResult testResult1 = new TestResult(2, 0, 1, 0, 10, "a test", Arrays.asList(
                                                new TestCaseResult("testClass1", "test-name", true, 11),
                                                new TestCaseResult("another class", "another test", false, 40)));
        TestResult testResult2 = new TestResult(2, 0, 1, 0, 10, "a test", Arrays.asList(
                                                new TestCaseResult("testClass1", "test-name", true, 11),
                                                new TestCaseResult("another class", "another test", false, 40)));
        
        assertThat(testResult1, is(testResult2));
        assertThat(testResult1.hashCode(), is(testResult2.hashCode()));
    }
    
    @Test
    public void differentTestResultsShouldBeInequalWithDifferentHashCodes() {
        TestResult testResult1 = new TestResult(2, 0, 0, 0, 10, "a test", Collections.<TestCaseResult> emptyList());
        TestResult testResult2 = new TestResult(3, 2, 0, 0, 10, "another test", Collections.<TestCaseResult> emptyList());
        
        assertThat(testResult1, is(not(testResult2)));
        assertThat(testResult1.hashCode(), is(not(testResult2.hashCode())));
    }
    
}
