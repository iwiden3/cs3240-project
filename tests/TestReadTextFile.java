package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import main.FileScanner;

import org.junit.Before;
import org.junit.Test;

public class TestReadTextFile {
	
	FileScanner fs;
	
	@Before
	public void setup() throws IOException{
		fs = new FileScanner("tests/SampleSpec");
	}
	
	@Test
	public void testDigit(){
		assertEquals("[0-9]", fs.getRegexTable().get("$DIGIT"));
	}

	@Test
	public void testNonZero(){		
		assertEquals("[0-9^0]", fs.getRegexTable().get("$NON-ZERO"));
	}
	
	@Test
	public void testChar(){
		assertEquals("[a-zA-Z]", fs.getRegexTable().get("$CHAR"));
	}
	
	@Test
	public void testUpper(){
		assertEquals("[a-zA-Z^a-z]", fs.getRegexTable().get("$UPPER"));
	}
	
	@Test
	public void testLower(){
		assertEquals("[a-zA-Z^A-Z]", fs.getRegexTable().get("$LOWER"));
	}
	
	@Test
	public void testIdentifier(){
		assertEquals("[a-zA-Z^A-Z] ([a-zA-Z^A-Z]|[0-9])*", fs.getTokenTable().get("$IDENTIFIER"));
	}
	
	@Test
	public void testInt(){
		assertEquals("([0-9])+", fs.getTokenTable().get("$INT"));
	}
	
	@Test
	public void testFloat(){
		assertEquals("([0-9])+ \\. ([0-9])+", fs.getTokenTable().get("$FLOAT"));
	}
	
	@Test
	public void testAssign(){
		assertEquals("=", fs.getTokenTable().get("$ASSIGN"));
	}
	
	@Test
	public void testPlus(){
		assertEquals("\\+", fs.getTokenTable().get("$PLUS"));
	}
	
	@Test
	public void testMinus(){
		assertEquals("-", fs.getTokenTable().get("$MINUS"));
	}
	
	@Test
	public void testMultiply(){
		assertEquals("\\*", fs.getTokenTable().get("$MULTIPLY"));
	}
	
	@Test
	public void testPrint(){
		assertEquals("PRINT", fs.getTokenTable().get("$PRINT"));
	}
}