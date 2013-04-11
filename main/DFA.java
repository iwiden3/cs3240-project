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
        HashMap<String, List<State>> transTable = s.getTransitionTable();

        for (State s : transTable.get(""))
        {
            eStates.add(s);

            for (State e : constructEClosure(s))
            {
                eStates.add(e);
            }
        }

        return eStates;
    }
}
