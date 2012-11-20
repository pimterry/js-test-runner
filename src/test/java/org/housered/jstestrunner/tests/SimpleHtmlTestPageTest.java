package org.housered.jstestrunner.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class SimpleHtmlTestPageTest {
	
	private static final String ROOT = withLeadingSlash(getRootPath());
	private static final String CURRENT_DIRECTORY = withLeadingSlash(getCurrentPath());
	
	private static String getRootPath() {
		String currentPath = getCurrentPath();
		
		for (File root : File.listRoots()) {
			if (currentPath.startsWith(root.getAbsolutePath())) {
				return root.getAbsolutePath().replace("\\", "/");
			}
		}
		throw new IllegalStateException("Cannot derive path to filesystem root");
	}
	
	private static String getCurrentPath() {
		try {
			return new File(".").getCanonicalPath();
		} catch (IOException e) {
			throw new IllegalStateException("Unable to get current directory for SimpleHtmlTestPage path tests");
		}		
	}
	
	private static String withLeadingSlash(String input) {
		if (input.startsWith("/")) {
			return input;
		} else {
			return "/" + input;
		}
	}

	@Test
	public void shouldProvidePathToTestFile() throws IOException {
		String actualFilePath = ROOT + "test/file/path.html";
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(new File(actualFilePath));
		String filePath = testPage.getFileURL();
		
		assertEquals("file://" + actualFilePath.replace("\\", "/"), filePath);
	}
	
	@Test
	public void shouldProvidePathToTestFileWithBackwardSlashes() throws IOException {
		String actualFilePath = ROOT + "test\\file\\path.html";
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(new File(actualFilePath));
		String filePath = testPage.getFileURL();
		
		assertEquals("file://" + actualFilePath.replace("\\", "/"), filePath);
	}
	
	@Test
	public void shouldEscapeSpecialCharactersInPathIfNecessary() throws IOException {
		String actualFilePath = ROOT + "test/file/path to file.html";
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(new File(actualFilePath));
		String filePath = testPage.getFileURL();
		
		assertEquals("file://" + actualFilePath.replace(" ", "%20").replace("\\", "/"), filePath);
	}		
	
	@Test
	public void shouldProvideAbsolutePathToTestFileInWorkingDirectory() throws IOException {
		String expectedAbsolutePath = "file://" + CURRENT_DIRECTORY.replace("\\", "/") + "/testFile.html";
		
		File relativeFile = new File("./testFile.html");		
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(relativeFile);
		
		assertEquals(expectedAbsolutePath, testPage.getFileURL());
	}
	
	@Test
	public void shouldProvideAbsolutePathToTestFileInSiblingDirectory() throws IOException {		
		String parentDirectory = withLeadingSlash(new File(CURRENT_DIRECTORY).getParent());
		String expectedAbsolutePath = "file://" + parentDirectory.replace("\\", "/") + "/sibling/testFile.html";
		
		File relativeFile = new File("../sibling/testFile.html");		
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(relativeFile);
		
		assertEquals(expectedAbsolutePath, testPage.getFileURL());
	}
	
	@Test
	public void shouldProvideAbsolutePathToTestFileInParentDirectory() throws IOException {				
		String parentDirectory = withLeadingSlash(new File(CURRENT_DIRECTORY).getParent());
		String expectedAbsolutePath = "file://" + parentDirectory.replace("\\", "/") + "/testFile.html";
		
		File relativeFile = new File("../testFile.html");		
		
		SimpleHtmlTestPage testPage = new SimpleHtmlTestPage(relativeFile);
		
		assertEquals(expectedAbsolutePath, testPage.getFileURL());
	}		
	
}
