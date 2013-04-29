package phase2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


public class LL1Parser
{
    private List<String> origFile;
    private LinkedHashMap<Token, HashSet<Token>> firstSets;
    private LinkedHashMap<Token, HashSet<Token>> followSets;
    private LinkedHashMap<String, LinkedHashMap<String, String>> parseTable;
    private String begin;

    private final static Charset ENCODING = StandardCharsets.US_ASCII;

	public LL1Parser()
	{
		begin=null;
		origFile = null;
		firstSets = null;
		followSets = null;
		parseTable = new LinkedHashMap<String, LinkedHashMap<String, String>>();
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getParseTable()
	{
		return parseTable;
	}

	public String getBegin()
	{
		return begin;
	}

	public LinkedHashMap<Token, HashSet<Token>> getFirstSets()
	{
		return firstSets;
	}
    
    public void inputFile(String file) throws IOException
    {
    	origFile = readTextFile(file);
    }
    
    public List<String> getInputFile()
    {
    	return origFile;
    }
    
    public void createFirstSets()
    {
    	int i = 0;
    	LinkedHashMap<Token, HashSet<Token>> map = new LinkedHashMap<Token, HashSet<Token>>();
    	HashSet<Token> keys = new HashSet<Token>();
    	Token t;
    	for(String str : origFile)
    	{	
    		str = replaceSpace(str);
    		String[] splitString = (str.split("::="));
        	//REMOVE SPACES
        	//splitString[0]= splitString[0].substring(0, splitString[0].length() - 3);
        	        	
        	HashSet<Token> set = getTerm(splitString[0],splitString[1]);
        	//terminating conditions are now in
        	
//        	for(Token t2 : set){
//        		if(t2.getValue().charAt(0)!='<' || t2.getValue().length() == 1){
//        			parseTable.get(splitString[0]).put(t2.getValue(), value);
//        		}
//        	}
        	
        	t = new Token(splitString[0],false,i==0);
        	if(i==0)
        	{
        		begin=splitString[0];
        	}
        	if(keys.contains(t)){
        		set = combineSet(map.get(t), set);
        	}
        	else{
        		keys.add(t);
        	}
        	map.put(t,set);
        	i++;
        }
    
    	//HashSet<String> keys= (HashSet<String>) map.keySet();
    
    	for(Token key : keys)
    	{
    		HashSet<Token> value = getStuff(map,key);
    		map.put(key, value);
    	}
    	firstSets = map;
    }

    public void createFollowSets()
    {
        String[] temp;
        List<String> file = origFile;
        HashSet<String> nonterminals = new HashSet<String>();

        for (String s : file)
        {
            temp = s.split(" ");
            nonterminals.add(temp[0]);
        }
        System.out.println(nonterminals);
    }
    
    
    public String replaceSpace(String str)
    {
    	String ret = "";
    	
    	for(int i=0;i<str.length();i++)
    	{
    		if(str.charAt(i)!=' ')
    		{
    			ret+=str.charAt(i);
    		}
    	}
    	return ret;
    }
    
    /*
    public LinkedHashMap<String, Set<String>> createFirstSets(List<String> origFile)
    {
    	LinkedHashMap<String, Set<String>> map=new LinkedHashMap<String,Set<String>>();
    	HashSet<String> keys=new HashSet<String>();
    	for(String str : origFile)
    	{	
    		str=replaceSpace(str);
    		String[] splitString = (str.split("="));
        	//REMOVE SPACES
        	splitString[0]= splitString[0].substring(0, splitString[0].length() - 1);
        	
        	HashSet<String> set=getTerm(splitString[0],splitString[1]);
        	//terminating conditions are now in
        	map.put(splitString[0], set);
        	keys.add(splitString[0]);
        }
    
    	//HashSet<String> keys= (HashSet<String>) map.keySet();
    
    	for(String key : keys)
    	{
    		HashSet<String> value=getstuff(map,key);
    		map.put(key, value);
    		
    	}
    	return map;
    }
    */
    
    public HashSet<Token> getStuff(LinkedHashMap<Token,HashSet<Token>> map, Token key)
    {
    	HashSet<Token> set = (HashSet<Token>) map.get(key);
    	HashSet<Token> set2 = new HashSet<Token>();

    	if(set!=null)
    	{	
    		for(Token str : set)
    		{	
    			if(str.getValue().length()==1)
    			{
    				set2.add(str);
    			}
    			else if(str.getValue().substring(1,str.getValue().length()-1).equals("epsilon"))
    			{
    				str.toggle();
    				set2.add(str);
    			}
    			else if(str.getValue().charAt(0)!='<')
    			{
    				set2.add(str);
    			}
    			else
    			{
					String[] split2 = str.getValue().split(">");
					split2[0]+=">";
    				HashSet<Token> set3 = getStuff(map,new Token(split2[0], false, false));
    				addToParseTable(set3, str, key.getValue());
    				for(Token t : set3)
    				{
    					set2.add(t);
    				}
    			}
    		}	
    	}
    	return set2;
    }
    
	public HashSet<Token> getTerm(String key,String str)
	{
		HashSet<Token> set = new HashSet<Token>();
		String[] split = str.split("\\|");
		for(String temp : split)
		{
			Token t;
			if(!temp.isEmpty())
			{
				if(temp.charAt(0)!='<')
				{
					String[] split2 = temp.split("<");
					t = new Token(split2[0],true,false);
					HashSet<Token> h = new HashSet<Token>();
					h.add(t);
					addToParseTable(h, new Token(temp,false,false), key);
					set.add(t);
				}
				else
				{
					String[] split2 = temp.split(">");
					split2[0]+=">";
					if(!split2[0].equals(key))
					{	
						t = new Token(temp,false,false);
						set.add(t);
					}
					/*
					else
					{
						split2[1]+=">";
						set.add(split2[1]);

					}*/
				}
			}
		}
	    return set;
	}
    
	private <T> HashSet<T> combineSet(HashSet<T> set1, HashSet<T> set2)
	{
		for(T str :set2)
		{
			set1.add(str);
		}
		return set1;
	}
	
	private void addToParseTable(HashSet<Token> hs, Token str, String key){
		Set<String> ks = parseTable.keySet();
		if(!ks.contains(key)){
    		parseTable.put(key, new LinkedHashMap<String,String>());
		}
    	for(Token t2 : hs){
			if(t2.getValue().charAt(0)!='<' || t2.getValue().length() == 1){
				parseTable.get(key).put(t2.getValue(), str.getValue());
			}
    	}	
	}
    
    private List<String> readTextFile(String aFileName) throws IOException
	{
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}
}