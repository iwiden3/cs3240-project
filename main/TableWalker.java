package main;

import java.util.*;


public class TableWalker
{
	private DFA dfa;
		
	public TableWalker(DFA dfa)
	{
		this.dfa = dfa;
	}

    // Takes in a string and validates it against the DFA
    // Returns a character or token class if valid
    // Returns null if invalid
    public String validate(String token)
    {
        return "";
    }
}
