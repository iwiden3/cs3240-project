package main;

import java.util.*;

public class NFAFactory
{
	LinkedHashMap<String, String> regexTable;
	LinkedHashMap<String, String> tokenTable;

	// Will take in a table
	public NFAFactory(LinkedHashMap<String, String> regexTable, LinkedHashMap<String, String> tokenTable)
	{
		this.regexTable = regexTable;
		this.tokenTable = tokenTable;
	}

	public HashSet<NFA> factorize()
	{
		LinkedHashSet<NFA> regexNFAs = new LinkedHashSet<NFA>();
		Set<String> keys1 = regexTable.keySet();
		for(String key : keys1)
		{
			String value = regexTable.get(key);
			NFACreator create = new NFACreator(key, value, regexTable, null);
			regexNFAs.add(create.getNFA());
		}
		
		HashSet<NFA> nfaSet = new HashSet<NFA>();
		Set<String> keys2 = tokenTable.keySet();
		for(String key : keys2)
		{
			String value = tokenTable.get(key);
			NFACreator create = new NFACreator(key, value, regexTable, regexNFAs);
			nfaSet.add(create.getNFA());
		}
		return nfaSet;
	}
}