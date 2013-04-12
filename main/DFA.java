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
        NFA nfa = bnfa.getNFA();
        convertToDFA(nfa);
	}

    private void convertToDFA(NFA nfa)
    {
        start = findStart(nfa);
    }

    // Calculates the start state of the DFA
    private DFAState findStart(NFA nfa)
    {
        start = constructEClosure(nfa.getStart());

        return null;
    }

    private DFAState constructEClosure(State s)
    {
        SortedSet<State> eStates = new TreeSet<State>();
        SortedSet<State> finalStates; 

        finalStates = constructEClosure(s, eStates);

        return null;
    }

    private SortedSet<State> constructEClosure(State curr, SortedSet<State> eStates)
    {
        if (!eStates.contains(curr))
        {
            eStates.add(curr);
            if (curr.getTransitionTable().containsKey(""))
            {
                for (State s : curr.getTransitionTable().get(""))
                {
                    constructEClosure(s, eStates);
                }
            }
        }

        return eStates;
    }

}
