package main;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TableWalker
{
	private NFA nfa;
		
	public TableWalker(BigNFA nfa)
	{
		this.nfa = nfa.getNFA();
	}

    // Takes in a string and validates it against the NFA
    // Returns a character or token class if valid
    // Returns null if invalid
    public String validate(String token)
    {
        // Set up lists for tracking current valid states
        // and current acceptStates
        List<WalkerPair> currStates = new ArrayList<WalkerPair>();
        List<String> acceptStates = new LinkedList<String>();
        WalkerPair currPair;

        State start = nfa.getStart();
        HashTable<String, List<State>> startTable = start.getTransitionTable();

        // Add all epsilon transitions coming out of start
        for (State s : startTable.get(""))
        {
            currPair = new WalkerPair(s.getName(), s);
            currStates.add(currPair);
        }
        

        return "";
    }
}
