package org.housered.jstestrunner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations="classpath:applicationcontext.xml")
public class SpringWiringTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void ensureSpringContextLoadedSuccessfully() {
		assertNotNull(applicationContext.getBean(CommandLineTool.class));
	}
	
}
