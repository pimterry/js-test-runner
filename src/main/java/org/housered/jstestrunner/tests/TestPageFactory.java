package org.housered.jstestrunner.tests;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class TestPageFactory
{   

    public TestPage getTestPage(String testPagePath) throws IOException {
        return new SimpleHtmlTestPage(testPagePath);
    }
    
}
