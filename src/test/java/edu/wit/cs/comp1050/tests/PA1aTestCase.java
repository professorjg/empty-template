package edu.wit.cs.comp1050.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.jupiter.api.*;

import edu.wit.cs.comp1050.PA1a;

@Timeout(1)
public class PA1aTestCase {
	
	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	
	private static class NoExitSecurityManager extends SecurityManager 
    {
        @Override
        public void checkPermission(Permission perm) {}
        
        @Override
        public void checkPermission(Permission perm, Object context) {}
        
        @Override
        public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
    }
	
	@BeforeEach
    public void setUp() 
    {
        System.setSecurityManager(new NoExitSecurityManager());
    }
	
	@AfterEach
    protected void tearDown() 
    {
        System.setSecurityManager(null);
    }
	
	@Test
	public void testOutput() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final String expected = "Hello World!";
		
		System.setOut(new PrintStream(outContent));
		try {
			PA1a.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(String.format("%s%n", expected), outContent.toString());
		
		System.setOut(null);
	}

}
