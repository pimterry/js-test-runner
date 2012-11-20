package org.housered.jstestrunner.tests;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SimpleHtmlTestPage implements TestPage {

    private String testPageUrl;
    private File htmlFile;

    public SimpleHtmlTestPage(File htmlFile) throws IOException {
        this.htmlFile = htmlFile;
        this.testPageUrl = buildFileURL(htmlFile);
    }
    
    private String buildFileURL(File file) throws IOException {
        String absolutePath = file.getCanonicalPath();

        if (!absolutePath.startsWith("/")) {
            absolutePath = "/" + absolutePath;
        }

        try {
            URI uri = new URI("file", "", absolutePath.replace("\\", "/"), null);
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to build URI for test file", e);
        }
    }
    
    public String getFilePath() {
        return htmlFile.getPath();
    }

    public String getFileURL() {
        return testPageUrl;
    }

}
