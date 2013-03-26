package tests;

import static org.junit.Assert.*;
import java.io.IOException;
import main.FileScanner;
import org.junit.Test;

public class TestReadTextFile {

	@Test
	public void test() throws IOException{
		FileScanner fs = new FileScanner("tests/SampleSpec");
		assertEquals(fs.getRegexTable().get("$DIGIT"), "[0-9]");
		assertEquals(fs.getRegexTable().get("$NON-ZERO"), "[0-9^0]");
		assertEquals(fs.getRegexTable().get("$CHAR"), "[a-zA-Z]");
		assertEquals(fs.getRegexTable().get("$UPPER"), "[a-zA-Z^a-z]");
		assertEquals(fs.getRegexTable().get("$LOWER"), "[a-zA-Z^A-Z]");
		assertEquals(fs.getTokenTable().get("$IDENTIFIER"), "$LOWER ($LOWER|$DIGIT)*");
		assertEquals(fs.getTokenTable().get("$MINUS"), "-");
	}

}
