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

		ArrayList<String> list=new ArrayList<String>();
		list.add("<cool>:=<hello>|<epsilon>");
		list.add("<hello>:=begin|<pawl>");
		list.add("<pawl>:=nice|koitus");
		
		HashMap<String, Set<String>> map=parse.createFirstSets(list);
		Set<String> keys=map.keySet();
		
		for(String key : keys)
		{
			System.out.println(key + " : " + setToString(map.get(key)));
		}

		
		
		
		
	}
	
	public static String setToString(Set<String> set)
	{
		String ret="";
		
		for(String s : set)
		{
			ret+=s;
			ret+=" ";
		}
		
		
		return ret;
	}
	
	
}