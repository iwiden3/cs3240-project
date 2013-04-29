package phase2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import main.DriverNFA;

public class finalDriver
{
    private HashMap<String, HashMap<String, String>> parseTable;
    private List<String> tokens;
    private List<String> input;
    private List<String> output;
    private final static Charset ENCODING = StandardCharsets.US_ASCII;

	
	public finalDriver(String grammar,String input,String spec) throws IOException
	{
		DriverNFA dr=new DriverNFA(input,spec);
		tokens=readTextFile("tests/output.txt");
		
		
	}

	public HashMap<String, HashMap<String, String>> getParseTable()
	{
		return parseTable;
	}
	
	public List<String> getTokens()
	{
		return tokens;
	}
	
	public List<String> getInput()
	{
		return input;
	}
	
	public List<String> getOutput()
	{
		return output;
	}
	







	  private List<String> readTextFile(String aFileName) throws IOException
	  {
		  Path path = Paths.get(aFileName);
		  return Files.readAllLines(path, ENCODING);
	  }


}