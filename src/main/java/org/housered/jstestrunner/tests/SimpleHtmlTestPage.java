package org.housered.jstestrunner.tests;

import java.io.File;
import java.io.IOException;

public class SimpleHtmlTestPage implements TestPage
{

    private File htmlFile;

    public SimpleHtmlTestPage(String htmlFilePath) throws IOException
    {
    	this.htmlFile = new File(new File(htmlFilePath).getCanonicalPath());    	
    }

    public String getFilePath()
    {
        return this.htmlFile.toURI().toString();
    }

}
