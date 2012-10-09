package org.housered.jstestrunner.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class SimpleHtmlTestPageTest {
	
	private static final String ROOT = getRootPath();
	private static final String CURRENT_DIRECTORY = getCurrentPath();
	
	private static String getRootPath() {
		String currentPath = getCurrentPath();
		
		for (File root : File.listRoots()) {
			if (currentPath.startsWith(root.getAbsolutePath())) {
				return root.getAbsolutePath();
			}
		}
		return null;
	}
	
	private static String getCurrentPath() {
		try {
			return new File(".").getCanonicalPath();
		} catch (IOException e) {
			throw new IllegalStateException("Unable to get current directory for SimpleHtmlTestPage path tests");
		}		
	}

	@Test
	public void shouldProvidePathToTestFile() throws IOException {
		String actualFilePath = new File(ROOT, "test\\file\\path.html").getAbsolutePath();
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(actualFilePath);
		String filePath = testPage.getFilePath();
		
		assertEquals("file:/" + actualFilePath.replace("\\", "/"), filePath);
	}
	
	@Test
	public void shouldProvideAbsolutePathToTestFileInWorkingDirectory() throws IOException {		
		String relativePath = "./testFile.html";
		String expectedAbsolutePath = "file:/" + CURRENT_DIRECTORY.replace("\\", "/") + "/testFile.html";
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(relativePath);
		
		assertEquals(expectedAbsolutePath, testPage.getFilePath());
	}
	
	@Test
	public void shouldProvideAbsolutePathToTestFileInSiblingDirectory() throws IOException {		
		String relativePath = "../sibling/testFile.html";
		
		String parentDirectory = new File(CURRENT_DIRECTORY).getParent();
		String expectedAbsolutePath = "file:/" + parentDirectory.replace("\\", "/") + "/sibling/testFile.html";
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(relativePath);
		
		assertEquals(expectedAbsolutePath, testPage.getFilePath());
	}
	
	@Test
	public void shouldProvideAbsolutePathToTestFileInParentDirectory() throws IOException {		
		String relativePath = "../testFile.html";
		
		String parentDirectory = new File(CURRENT_DIRECTORY).getParent();
		String expectedAbsolutePath = "file:/" + parentDirectory.replace("\\", "/") + "/testFile.html";
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(relativePath);
		
		assertEquals(expectedAbsolutePath, testPage.getFilePath());
	}		
	
}
