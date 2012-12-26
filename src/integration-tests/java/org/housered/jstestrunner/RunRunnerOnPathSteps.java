package org.housered.jstestrunner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class RunRunnerOnPathSteps {
	
	private String targetPath;
	private String targetPathPlaceholder;
	
	@Given(".*'(.+)' (\\[.+\\]) (?:is|contains|exists).*")
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
	
	@Given("'(.*)' does not exist")
	public void pathDoesNotExist(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}
	
	@Then("'(.*)' should not exist")
	public void pathShouldNotExist(String path) {
		assertFalse(new File(path).exists());
	}

}
