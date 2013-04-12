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
        DFAState dummy = new DFAState("", false, null, new HashMap<String, State>());
        dummy.addState(nfa.getStart());
        start = closure(dummy);
    }

    // Calculates epsilon transition states
    private DFAState closure(DFAState s)
    {
        HashSet<State> ret = new HashSet<State>();
        HashSet<State> toAdd;
        boolean isAccept = false;
        String name = "";
        DFAState output;

        // Add all input states to output
        for (State i : s.getStates())
        {
            ret.add(i);
        }

        while (true)
        {
            toAdd = new HashSet<State>();

            // Iterate over all states currently in ret
            for (State r : ret)
            {
                if (r.getTransitionTable().containsKey(""))
                {
                    for (State t : r.getTransitionTable().get(""))
                    {
                        if (!ret.contains(t))
                        {
                            toAdd.add(t);
                        }
                    }
                }
            }
            if (toAdd.isEmpty())
            {
                break;
            }
            for (State a : toAdd)
            {
                ret.add(a);
            }
        }
        // Determine if new state is accept
        for (State t : ret)
        {
            if (t.isAccept())
            {
                isAccept = true;
                name = t.getName();
                break;
            }
        }
        // Create new DFAState
        output = new DFAState(name, isAccept, ret, new HashMap<String, State>());
        states.add(output);
        if (output.isAccept())
        {
            accept.add(output);
        }

        return output;
    }

    private DFAState goTo(DFAState s)
    {
        return null;
    }
}
