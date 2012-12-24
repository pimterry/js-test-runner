package org.housered.jstestrunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import cucumber.annotation.en.Then;

public class RealQUnitTestSuites {
    
    @Then("'(.+)' should describe the .+ tests passing")
    public void allTestsShouldPass(String outputFile) throws Exception {
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
