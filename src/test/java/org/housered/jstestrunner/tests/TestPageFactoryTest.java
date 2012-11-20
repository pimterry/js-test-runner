package org.housered.jstestrunner.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.housered.jstestrunner.io.TestFileTypeDetector.TestFileType.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.housered.jstestrunner.io.IOManager;
import org.housered.jstestrunner.io.TestFileTypeDetector;
import org.housered.jstestrunner.io.TestFileTypeDetector.TestFileType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestPageFactoryTest {

    @Mock TestFileTypeDetector testTypeDetector;
    @Mock IOManager ioManager;
    @InjectMocks TestPageFactory factory;
    
    @Test
    public void shouldReturnSingleHTMLTestPage() throws IOException {
        String testFilePath = "\\dir\\testpath\\test.html";
        when(ioManager.isFileADirectory(any(File.class))).thenReturn(false);
        when(testTypeDetector.detectFileType(any(File.class))).thenReturn(TestFileType.QUNIT);
    
        List<TestPage> testPages = factory.getTestPages(testFilePath);
    
        assertEquals(1, testPages.size());
        assertEquals(testFilePath, testPages.get(0).getFilePath());        
    }    

    @Test
    public void shouldReturnFoundHtmlPages() throws IOException {
    	String root = "/dir/testpath/";
    
    	File testFile = mockFile(root, "testfile.html", TestFileType.QUNIT);
    	File testFile2 = mockFile(root, "other test.html", TestFileType.QUNIT);
    
    	List<File> foundFiles = Arrays.asList(mockFile(root, "README", UNKNOWN), 
    	                                      testFile, 
    	                                      mockFile(root, "otherfile.docx", UNKNOWN),
    	                                      mockFile(root, "a.pdf", UNKNOWN), 
    	                                      testFile2);
    	when(ioManager.isFileADirectory(any(File.class))).thenReturn(true);
    	when(ioManager.listFiles(root)).thenReturn(foundFiles);
    
    	List<TestPage> testPages = factory.getTestPages(root);
    
    	assertEquals(2, testPages.size());
    	assertEquals(testFile.getPath(), testPages.get(0).getFilePath());
    	assertEquals(testFile2.getPath(), testPages.get(1).getFilePath());
    }
    
    @Test
    public void shouldReturnEmptyListIfNoTestsFoundForDirectory() throws IOException {
        String root = "\\dir\\emptypath";
        
        List<File> foundFiles = Arrays.asList(mockFile(root, "README", UNKNOWN), 
                                              mockFile(root, "otherfile.docx", UNKNOWN));
        
        when(ioManager.isFileADirectory(any(File.class))).thenReturn(true);
        when(ioManager.listFiles(root)).thenReturn(foundFiles);
    
        List<TestPage> testPages = factory.getTestPages(root);
    
        assertEquals(0, testPages.size());
    }
    
    @Test
    public void shouldReturnEmptyListIfSingleSpecifiedTestIsntValid() throws IOException {
        String root = "\\dir\\testpath\\badtest.pdf";
        when(ioManager.isFileADirectory(any(File.class))).thenReturn(false);
        when(testTypeDetector.detectFileType(any(File.class))).thenReturn(TestFileType.UNKNOWN);
    
        List<TestPage> testPages = factory.getTestPages(root);
    
        assertEquals(0, testPages.size());
    }
    
    private File mockFile(String root, String name, TestFileType fileType) throws IOException {
        File file = new File(root, name);        
        when(testTypeDetector.detectFileType(file)).thenReturn(fileType);        
        return file;        
    }

}
