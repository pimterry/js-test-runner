package org.housered.jstestrunner;

import static ch.qos.logback.classic.Level.INFO;
import static ch.qos.logback.classic.Level.WARN;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import cucumber.annotation.en.Then;

public class ConsoleOutputSteps {

	private final Appender<ILoggingEvent> mockAppender;
	
	@SuppressWarnings("unchecked")
	public ConsoleOutputSteps() {
		Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    mockAppender = mock(Appender.class);
	    
	    when(mockAppender.getName()).thenReturn("MOCK");
	    rootLogger.addAppender(mockAppender);
	}
	
	private List<ILoggingEvent> logEvents() {
		ArgumentCaptor<ILoggingEvent> eventCaptor = ArgumentCaptor.forClass(ILoggingEvent.class);		
		verify(mockAppender, atLeastOnce()).doAppend(eventCaptor.capture());		
		return eventCaptor.getAllValues();
	}
	
	private Set<String> logMessagesOfAtLeastLevel(Level level) {
		Set<String> messages = new HashSet<String>();
		for (ILoggingEvent event : logEvents()) {
			if (event.getLevel().isGreaterOrEqual(level)) {
				messages.add(event.getFormattedMessage());
			}
		}
		return messages;
	}
	
	@Then("the console output should contain no warnings or errors")
	public void consoleOutputShouldContainNoWarningsOrErrors() {
		assertTrue(logMessagesOfAtLeastLevel(WARN).isEmpty());
	}

	@Then("the console output should contain '(.*)'")
	public void consoleOutputShouldContain(String expectedOutput) {
		assertThat(logMessagesOfAtLeastLevel(INFO), hasItem(containsString(expectedOutput)));
	}
	
}
