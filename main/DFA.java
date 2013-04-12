package main;

import java.util.*;


public class DFA
{
	public DFAState start;
	public List<DFAState> accept;
    public HashSet<DFAState> states;

    // This will call convertToDFA() using the passed in BigNFA
	public DFA(BigNFA bnfa)
	{
        // Extract the underlying NFA
        NFA nfa = bnfa.getNFA();
        // Set the DFA's start equal to an equivalent 
        convertToDFA(nfa);
	}

    // Calculates the start state of the DFA
    private void findStart(NFA nfa)
    {

    }

    // This sets the start/accept class variables
    private void convertToDFA(BigNFA nfa)
    {

    }

    private HashSet<State> constructEClosure(State s)
    {
        return null;
    }
}
