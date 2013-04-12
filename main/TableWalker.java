package main;

import java.util.*;


public class TableWalker
{
	private BigNFA nfa;
		
	public TableWalker(BigNFA nfa)
	{
		this.nfa = nfa;
	}

    // Takes in a string and validates it against the DFA
    // Returns a character or token class if valid
    // Returns null if invalid
    public String validate(String token)
    {
        return "";
    }
}
