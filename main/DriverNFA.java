package main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DriverNFA {
    private final static Charset ENCODING = StandardCharsets.US_ASCII;
    
	DFA dfa;
	String input, rules;
	List<String> text;
    TableWalkerNFA tw;
	
	public DriverNFA(String input, String rules)
	{
		this.input = input;
		this.rules = rules;
	 }
	
	public void start() throws IOException{
		FileScanner fs = new FileScanner(rules);
		NFAFactory factory = new NFAFactory(fs.getRegexTable(), fs.getTokenTable());
		HashSet<NFA> nfas = factory.factorize();
		text = readTextFile(input);
	    tw = new TableWalkerNFA(nfas,text);

		List<Token> list = tw.parse();
	    ArrayList<String> out = new ArrayList<String>();
	    
	    for(Token t : list)
	    {
	    	String str = t.getId();
	    	if(str.length() > 0){
	    		str = str.substring(1);
	    	}
	    	str += " ";
	    	str += t.getValue();
	    	str += "\r\n";
	    	out.add(str);
	    }
	    
	    // Edit this to change where the output is saved.
		FileWriter writer = new FileWriter("tests/output"); 
		for(String str: out) 
		{
		  writer.write(str);
		}
		writer.close();
	}
	
    private List<String> readTextFile(String aFileName) throws IOException{
        Path path = Paths.get(aFileName);
        List<String> temp = Files.readAllLines(path, ENCODING);
        List<String> toRet = new ArrayList<String>();
        for(String str : temp){
        	toRet.addAll(new ArrayList<String>(Arrays.asList(str.split(""))));
        }
        return toRet;
    }
}