package main;

import java.util.*;

public class BigNFA
{
	private NFA nfa;

	public BigNFA(HashSet<NFA> NFATable)
	{
		nfa = conversion(NFATable);
	}

    // Converts a set of NFA's into one giant NFA
    // Adds epsilon transitions from a new start state to each NFA
    // Adds epsilon transitions from all accept states to one new accept state
	private NFA conversion(HashSet<NFA> NFATAble)
	{

		return null;
	}
}
