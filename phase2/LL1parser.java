package phase2;

import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class LL1parser
{
    private List<String> origFile;
    private HashMap<String,Set<String>> firstSets;
    private HashMap<String,Set<String>> followSets;

    private final static Charset ENCODING = StandardCharsets.US_ASCII;

	public LL1parser()
	{
		origFile=null;
		firstSets=null;
		followSets=null;
	}
    
    public void inputFile(String file) throws IOException
    {
    	origFile=readTextFile(file);
    }
    
    public List<String> getInputFile()
    {
    	return origFile;
    }
    
    public void createFirstSets()
    {
    	HashMap<String, Set<String>> map=new HashMap<String,Set<String>>();
    	
    	for(String str : origFile)
    	{	
        	String[] splitString = (str.split("::="));
        	//REMOVE SPACES
        	HashSet<String> set=getTerm(splitString[1]);
        	//terminating conditions are now in
        	map.put(splitString[0], set);
        }
    
    	HashSet<String> keys= (HashSet<String>) map.keySet();
    
    	for(String key : keys)
    	{
    		HashSet<String> value=getstuff(map,key);
    		map.put(key, value);
    		
    	}
    }
    
    public HashSet<String> getstuff(HashMap<String,Set<String>> map, String key)
    {
    	HashSet<String> set=(HashSet<String>) map.get(key);
    	HashSet<String> set2=new HashSet<String>();

    	for(String str : set)
    	{
    		if(str.charAt(0)!='<')
    		{
    			set2.add(str);
    		}
    		else
    		{
    			HashSet<String> set3=getstuff(map,str);
    			for(String t : set3)
    			{
    				set2.add(t);
    			}
    		}
    	}
    	return set2;
    }
    
    public void createFollowSets()
    {
    	
    }
    
    
    
    
	public HashSet<String> getTerm(String str)
	{
		HashSet<String> set=new HashSet<String>();
		String[] split=str.split("|");
		for(String temp : split)
		{
			if(!temp.isEmpty())
			{
				if(temp.charAt(0)!='<')
				{
					String[] split2=temp.split("<");
					set.add(split2[0]);
				}
				else
				{
					String[] split2=temp.split(">");
				}
			
			}
		}
	    return set;
	}
    
	private HashSet<String> combineSet(HashSet<String> set1, HashSet<String> set2)
	{
		for(String str :set2)
		{
			set1.add(str);
		}
		return set1;
	}
    
	
    
    private List<String> readTextFile(String aFileName) throws IOException
	{
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}






}