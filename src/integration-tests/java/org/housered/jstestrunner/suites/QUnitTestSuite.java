package org.housered.jstestrunner.suites;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.housered.jstestrunner.App;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class QUnitTestSuite {
    
    @Given("the QUnit test suite is at '(.+/index.html)'")
    public void ensureQUnitSuiteAvailable(String path) {
        assertTrue(new File(path).exists());
    }
    
    @When("a user runs '(.+)'")
    public void runQUnitSuite(String command) throws IOException {
        String[] commandTokens = command.split(" ");
        String[] commandArguments = Arrays.copyOfRange(commandTokens, 1, commandTokens.length); 
        
        App.main(commandArguments);
    }
    
    @Then("'(.+)' should describe the qunit tests passing")
    public void allTestsShouldPass(String outputFile) throws JDOMException, IOException {
        SAXBuilder sax = new SAXBuilder();
        Document outputDoc = sax.build(new File(outputFile));
        
        Element root = outputDoc.getRootElement();
        
        assertEquals("testsuite", root.getName());
        assertThat(root.getAttribute("time").getFloatValue(), greaterThan(0f));
        
        assertTestSuiteElementIsCompleteAndPassing(root);
    }

    private void assertTestSuiteElementIsCompleteAndPassing(Element element) throws DataConversionException {
        assertFalse(element.getAttribute("name").getValue().isEmpty());        
        assertEquals(0, element.getAttribute("errors").getIntValue());
        assertEquals(0, element.getAttribute("failures").getIntValue());
        assertEquals(0, element.getAttribute("skip").getIntValue());
        
        for (Element testElement : element.getChildren()) {
            if (testElement.getName().equals("testsuite")) {
                assertTestSuiteElementIsCompleteAndPassing(testElement);
            } else {
                assertTestResultElementIsCompleteAndPassing(testElement);
            }
        }
    }
    
    private void assertTestResultElementIsCompleteAndPassing(Element element) throws DataConversionException {
        assertNull(element.getChild("failure"));
        assertNull(element.getChild("error"));
        assertNull(element.getChild("skipped"));

        assertFalse(element.getAttribute("name").getValue().isEmpty());
    }
    
}
