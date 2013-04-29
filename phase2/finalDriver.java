package phase2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import main.DriverNFA;

public class finalDriver
{
    private HashMap<String, HashMap<String, String>> parseTable;
    private ArrayList<String> tokens;
    private List<String> input;
    private String begin;
    private final static Charset ENCODING = StandardCharsets.US_ASCII;

	
	@SuppressWarnings("unchecked")
	public finalDriver(String grammar,String input,String spec) throws IOException
	{
		DriverNFA dr=new DriverNFA(input,spec);
		dr.start();
		tokens=(ArrayList<String>)readTextFile("tests/output.txt");
		LL1parser parser=new LL1parser();
		parser.inputFile(grammar);
		parser.createFirstSets();
		parser.createFollowSets();
		parseTable=parser.getParseTable();
		this.input=readTextFile("tests/input.txt");
		begin=parser.getBegin();
		
	}

	public void goDriverGo()
	{
		boolean fin=goDriverGo(begin,0);
		if(fin)
		{
			System.out.println("Pass");
		}
		
		
	}
	
	
	public boolean goDriverGo(String key,int i)
	{

		if(key.equals("$"))
		{
			return true;
		}
		else
		{
			HashMap<String,String> table=parseTable.get(key);
			//get what we are looking for
			String str=tokens.get(i);
			String[] splitString=str.split(" ");
			String pattern=table.get(splitString[0]);
			if(pattern==null)
			{
				System.out.println("Error with token: "+ splitString[1]);
				return false;
			}

			String[] splitString2=str.split(splitString[1]);
			String temp=splitString2[1];
			String cool = null;
			if(!temp.isEmpty())
			{
				if(temp.charAt(0)!='<')
				{
					String[] split2 = temp.split("<");
					cool=split2[0];
				}
				else
				{
					String[] split2 = temp.split(">");
					split2[0]+=">";
					cool=split2[0];
					
				}
			}
			i++;
			return goDriverGo(cool,i);
		}
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
	

	private List<String> readTextFile(String aFileName) throws IOException
	  {
		  Path path = Paths.get(aFileName);
		  return Files.readAllLines(path, ENCODING);
	  }


}