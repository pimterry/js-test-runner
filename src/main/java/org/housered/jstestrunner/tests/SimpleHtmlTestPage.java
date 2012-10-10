package org.housered.jstestrunner.tests;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SimpleHtmlTestPage implements TestPage
{

    private URI testPageAddress;

    public SimpleHtmlTestPage(String htmlFilePath) throws IOException
    {
    	String absolutePath = new File(htmlFilePath).getCanonicalPath();
    	
    	if (!absolutePath.startsWith("/")) {
    		absolutePath = "/" + absolutePath;
    	}
	
		try {
			testPageAddress = new URI("file", "", absolutePath.replace("\\", "/"), null);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Failed to build URI for test file", e); 
		}
    }

    public String getFilePath()
    {
		return testPageAddress.toASCIIString();
    }

}
