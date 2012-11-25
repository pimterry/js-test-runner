package org.housered.jstestrunner.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TestSuiteResultTest {

    @Test
    public void testConstructorAndGetters() {
        int tests = 10;
        int errors = 5;
        int failures = 2;
        int skipped = 1;
        int totalTime = 321;
        
        String name = "test-name";
        
        TestResult case1 = testCase(1);
        TestResult case2 = testCase(2);
        List<? extends TestResult> results = Arrays.asList(case1, case2);
        
        TestSuiteResult testResult = new TestSuiteResult(name, totalTime, tests, failures, errors, skipped, results);
        
        assertEquals(name, testResult.getName());
        assertEquals(tests, testResult.getTotalTestCount());
        assertEquals(errors, testResult.getErrors());
        assertEquals(failures, testResult.getFailures());
        assertEquals(skipped, testResult.getSkipped());
        assertEquals(totalTime, testResult.getTotalTime());
        
        assertSame(case1, testResult.getTestResults().get(0));
        assertSame(case2, testResult.getTestResults().get(1));
    }
    
    @Test
    public void emptyTestSuitesShouldBeEqual() {
        TestResult testResult1 = new TestSuiteResult("empty test", 10, 0, 0, 0, 0, Collections.<TestResult> emptyList());
        TestResult testResult2 = new TestSuiteResult("empty test", 10, 0, 0, 0, 0, Collections.<TestResult> emptyList());
        
        assertEquals(testResult1, testResult2);
    }
    
    @Test
    public void suitesContainingEqualSuitesShouldBeEqual() {
        TestSuiteResult containedTestSuite1 = new TestSuiteResult("t", 1, 2, 3, 4, 5, Collections.<TestResult> emptyList());
        TestSuiteResult containedTestSuite2 = new TestSuiteResult("t", 1, 2, 3, 4, 5, Collections.<TestResult> emptyList());
        
        TestResult suiteResult1 = new TestSuiteResult("empty test", 10, 0, 0, 0, 0, Arrays.asList(containedTestSuite1));
        TestResult suiteResult2 = new TestSuiteResult("empty test", 10, 0, 0, 0, 0, Arrays.asList(containedTestSuite2));
        
        assertEquals(suiteResult1, suiteResult2);
    }
    
    @Test
    public void sameTestSuitesShouldBeEqualWithEqualHashCodes() {
        TestResult testResult1 = new TestSuiteResult("a test", 10, 2, 0, 1, 0, Arrays.asList(testCase(1), testCase(2)));
        TestResult testResult2 = new TestSuiteResult("a test", 10, 2, 0, 1, 0, Arrays.asList(testCase(1), testCase(2)));
        
        assertThat(testResult1, is(testResult2));
        assertThat(testResult1.hashCode(), is(testResult2.hashCode()));
    }
    
    @Test
    public void differentTestSuitesShouldBeInequalWithDifferentHashCodes() {
        TestResult testResult1 = new TestSuiteResult("a test", 10, 2, 0, 0, 0, Collections.<TestResult> emptyList());
        TestResult testResult2 = new TestSuiteResult("another test", 10, 3, 2, 0, 0, Collections.<TestResult> emptyList());
        
        assertThat(testResult1, is(not(testResult2)));
        assertThat(testResult1.hashCode(), is(not(testResult2.hashCode())));
    }
    
    private TestResult testCase(int id) {
        return new TestResult("test " + id, id, true);
    }
    
}
