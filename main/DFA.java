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
        findStart(nfa);
    }

    // Calculates the start state of the DFA
    private void findStart(NFA nfa)
    {
        start = constructEClosure(nfa.getStart());
    }

    private DFAState constructEClosure(State s)
    {
        SortedSet<State> eStates = new TreeSet<State>();
        SortedSet<State> finalStates; 
        DFAState ret;
        String retId = ""; // inefficient, so sue me
        boolean isAccept = false;
        String name = "";

        finalStates = constructEClosure(s, eStates);
        
        for (State t : finalStates)
        {
            if (t.isAccept())
            {
                isAccept = true;
                name = t.getName();
            }

            retId += t.getId();
        }
        ret = new DFAState(name, retId, isAccept, new HashMap<String, State>());
        states.add(ret);

        if (ret.isAccept())
            accept.add(ret);

        return ret;
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
