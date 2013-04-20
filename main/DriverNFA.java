package main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DriverNFA {
	DFA dfa;
	String input;
	List<String> text;
    private final static Charset ENCODING = StandardCharsets.US_ASCII;
    TableWalkerNFA tw;
	
	public DriverNFA(String input, String rules) throws IOException
	{
		this.input = input;
		FileScanner fs=new FileScanner(rules);
		NFAFactory factory = new NFAFactory(fs.getRegexTable(), fs.getTokenTable());
		HashSet<NFA> nfas = factory.factorize();
//		BigNFA theNFA=new BigNFA(nfas);
		text = readTextFile(input);
	    tw = new TableWalkerNFA(nfas,text);
	    ArrayList<String> out = new ArrayList<String>();
		
	    List<Token> list=tw.parse();
	    
	    for(Token t : list)
	    {
	    	String str = t.getId();
	    	str = str.substring(1);
	    	str += " ";
	    	str += t.getValue();
	    	str += "\r\n";
	    	out.add(str);
	    }
	    
		FileWriter writer = new FileWriter("tests/output"); 
		for(String str: out) 
		{
		  writer.write(str);
		}
		writer.close();
	 }
	
    private List<String> readTextFile(String aFileName) throws IOException{
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }
}
