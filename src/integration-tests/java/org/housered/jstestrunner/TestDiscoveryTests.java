package org.housered.jstestrunner;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import cucumber.annotation.en.Then;

public class TestDiscoveryTests {

	@Then("'(.*)' should describe (\\d+) test suites passing")
	public void shouldContainTestResults(String outputFile, int expectedSuiteCount) 
			throws Exception {
        Element root = new SAXBuilder().build(new File(outputFile)).getRootElement();
        
        if (expectedSuiteCount == 1) {
        	assertIsPassingTestSuite(root);
        } else {
	        assertEquals("testsuites", root.getName());
	        assertEquals(expectedSuiteCount, root.getChildren().size());
	        
	        for (Element suiteElement : root.getChildren()) {
	        	assertIsPassingTestSuite(suiteElement);
	        }
        }
	}

	private void assertIsPassingTestSuite(Element element) throws DataConversionException {
		assertEquals("testsuite", element.getName());
		assertEquals(0, element.getAttribute("errors").getIntValue());
        assertEquals(0, element.getAttribute("failures").getIntValue());
        assertEquals(0, element.getAttribute("skip").getIntValue());
	}
	
}
