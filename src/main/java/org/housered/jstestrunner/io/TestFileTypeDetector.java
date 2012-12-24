package org.housered.jstestrunner.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component
public class TestFileTypeDetector {
    
    private static final String HTML_MIME_TYPE = "text/html";
	private static final String QUNIT_CONTENT_MARKER = "qunit.js";
    private Tika tika;

    public TestFileTypeDetector() {
        this.tika = new Tika();
    }
	
	public enum TestFileType {
		QUNIT,
		FOLDER,
		UNKNOWN
	}
	
	public TestFileType detectFileType(File file) throws IOException {
		String testFileType = tika.detect(file);
		
		if (testFileType.equals(HTML_MIME_TYPE)) {
			String fileContent = FileUtils.readFileToString(file);
			if (fileContent.contains(QUNIT_CONTENT_MARKER)) {
				return TestFileType.QUNIT;
			}
		    return TestFileType.UNKNOWN;
		} else {
		    return TestFileType.UNKNOWN;
		}
	}

}