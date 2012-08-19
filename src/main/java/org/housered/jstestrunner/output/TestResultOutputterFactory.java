package org.housered.jstestrunner.output;

import org.springframework.stereotype.Component;

@Component
public class TestResultOutputterFactory
{

    public TestResultOutputter getTestResultOutputter() {
        return new NullTestResultOutputter();
    }

}
