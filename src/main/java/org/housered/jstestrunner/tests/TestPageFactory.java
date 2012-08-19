package org.housered.jstestrunner.tests;

import org.springframework.stereotype.Component;

@Component
public class TestPageFactory
{   

    public TestPage getTestPage(String testPagePath) {
        return new SimpleHtmlTestPage(testPagePath);
    }
    
}
