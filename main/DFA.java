package main;

import java.util.*


public class DFA
{
	public State start;
	public List<State> accept;

    // This will call convertToDFA() using the passed in BigNFA
	public DFA(BigNFA nfa)
	{
        converToDFA(nfa);
	}

    // This sets the start/accept class variables
    private void convertToDFA(BigNFA nfa)
    {

    }

    private HashSet<State> constructEClosure(State s)
    {
        HashSet<State> eStates = new HashSet<State>();
        HashSet<State> visited = new HashSet<State>();
        HashMap<String, List<State>> transTable = s.getTransitionTable();

        // Adds the original state to the set
        eStates.add(s);
        visited.add(s);

        // Iterate over all epsilon transitions and add them to the set
        for (State st : transTable.get(""))
        {
            eStates.add(st);

            // Add all epsilon transitions from the new states
            if (!visited.contains(st))
            {
                for (State e : constructEClosure(st))
                {
                    eStates.add(e);
                }
            }
            visited.add(st);
        }

        return eStates;
    }
}
