package org.housered.jstestrunner;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = {"pretty", "html:target/component-tests"}, monochrome = true)
public class RunCucumberTests {
}
