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

    private void constructEClosure(State s)
    {
        HashMap<String, State> transTable = s.getTransitionTable();


    }

}
