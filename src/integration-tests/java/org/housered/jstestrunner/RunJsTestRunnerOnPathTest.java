package org.housered.jstestrunner;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.When;

public class RunJsTestRunnerOnPathTest {
	
	private String targetPath;
	private String targetPathPlaceholder;
	
	@Given(".*'(.+)' (\\[.+\\]).*")
	public void givenTargetPath(String targetPath, String targetPathPlaceholder) {
		this.targetPath = targetPath;
		this.targetPathPlaceholder = targetPathPlaceholder;
		
		assertTrue(new File(targetPath).exists());
	}
	
	@When("a user runs '(.+)'")
    public void runQUnitSuite(String command) throws IOException {
		command = command.replace(targetPathPlaceholder, targetPath);
        String[] commandTokens = command.split(" ");
        String[] commandArguments = Arrays.copyOfRange(commandTokens, 1, commandTokens.length); 
        
        App.main(commandArguments);
    }

}
