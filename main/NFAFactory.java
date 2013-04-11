package main;

import java.util.*;

public class NFAFactory
{
	HashMap<String, String> table;

	//Will take in a table;		
	public NFAFactory(HashMap<String, String> table)
	{
		this.table = table;	
	}

	public HashSet<NFA> factorize()
	{
		HashSet<NFA> nfaSet = new HashSet<NFA>();
		Set<String> keys = table.keySet();
		for(String key : keys){
			String value = table.get(key);
			NFA create = new NFA(key, value);
			nfaSet.add(create);
		}
		return nfaSet;
	}

}
