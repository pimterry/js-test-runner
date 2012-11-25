package org.housered.jstestrunner.tests;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TestResult
{
    
    private final String name;
    private final int testDurationMillis;
    private final boolean success;

    public TestResult(String name, int testDurationMillis, boolean success) {
        this.name = name;
        this.testDurationMillis = testDurationMillis;
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public int getTestDurationMillis() {
        return testDurationMillis;
    }
    
    public boolean wasSuccess() {
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
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
