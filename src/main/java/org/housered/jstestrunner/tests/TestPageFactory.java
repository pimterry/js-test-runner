package org.housered.jstestrunner.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.housered.jstestrunner.io.IOManager;
import org.housered.jstestrunner.io.TestFileTypeDetector;
import org.housered.jstestrunner.io.TestFileTypeDetector.TestFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestPageFactory
{   
	private IOManager ioManager;
	private TestFileTypeDetector testFileTypeDetector;

	@Autowired
	public TestPageFactory(IOManager ioManager, TestFileTypeDetector testFileTypeDetector) {
		this.ioManager = ioManager;
		this.testFileTypeDetector = testFileTypeDetector;
	}
	
	public TestPage getTestPage(File testPageFile) throws IOException {
	    TestFileType fileType = testFileTypeDetector.detectFileType(testPageFile);
	    
	    switch(fileType) {
	        case QUNIT:
	            return new SimpleHtmlTestPage(testPageFile);
	        default:
	            return null;
	    }
	}

    public TestPage getTestPage(String testPagePath) throws IOException {
    	return getTestPage(new File(testPagePath));
    }

	public List<TestPage> getTestPages(String testPath) throws IOException {
		File testFile = new File(testPath);
		
		if (ioManager.isFileADirectory(testFile)) {
			List<TestPage> testPages = new ArrayList<TestPage>();
			
			for (File file : ioManager.listFiles(testPath)) {
			    TestPage testPage = getTestPage(file);			    
			    if (testPage != null) testPages.add(getTestPage(file));
			}
			
			return testPages;
		} else {
		    TestPage testPage = getTestPage(testPath);
		    if (testPage != null) {		    
		        return Collections.singletonList(getTestPage(testPath));
		    } else {
		        return Collections.emptyList();
		    }
		}
		
	}
    
}
