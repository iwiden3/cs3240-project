package phase2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HomeStarRunner {

	public static void main(String[]args) throws IOException
	{		
		LL1parser parse=new LL1parser();
		//HashSet<String> str=parse.getTerm("diff|union|inters");
		
		/*HashSet<String> set1=new HashSet<String>();
		HashSet<String> set2=new HashSet<String>();
		HashSet<String> set3=new HashSet<String>();

		HashMap<String, Set<String>> map=new HashMap<String,Set<String>>();
		set2.add("begin");
		set2.add("<pawl>");
		set1.add("<hello>");
		set3.add("nice");
		set3.add("koitus");
		map.put("<cool>",set1);
		map.put("<hello>",set2);
		map.put("<pawl>",set3);*/
		
		//HashSet<String> str= parse.getstuff(map,"<cool>");

		/*ArrayList<String> list=new ArrayList<String>();
		list.add("<cool>:=<hello>| <epsilon>");
		list.add("<hello> :=begin|<pawl>");
		list.add("<pawl>:=nice |koitus");*/
		
        System.out.println(args[0]);
		parse.inputFile(args[0]);
		parse.createFirstSets();
		
	    HashMap<Token, Set<Token>> map=parse.getFirstSets();
		Set<Token> keys=map.keySet();

        parse.createFollowSets();
		
		
		
		
		for(Token key : keys)
		{
			System.out.println(key.getValue() + " : " + setToString(map.get(key)));
		}

		
		
		
		
	}
	
	public static String setToString(Set<Token> set)
	{
		String ret="";
		
		for(Token s : set)
		{
			String s2=s.getValue();
			ret+=s2;
			ret+=" ";
		}
		
		
		return ret;
	}
	
	
}
