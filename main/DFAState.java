package main;

import java.util.*;

public class DFAState
{
    private String name;
    private boolean accept;
    private HashSet<State> states;
    private HashMap<String, State> transitionTable;

    public DFAState(String name, boolean accept, HashSet<State> states,
            HashMap<String, State> transitionTable)
    {
        this.name = name;
        this.accept = accept;
        this.transitionTable = transitionTable;
        this.states = states;
    }

    public boolean isAccept()
    {
        return accept;
    }

    public void addState(State s)
    {
        states.add(s);
    }

    public String getName()
    {
        return name;
    }

    public HashMap<String, State> getTransitions()
    {
        return transitionTable;
    }

    public HashSet<State> getStates()
    {
        return states;
    }

    public boolean equals(Object obj)
    {
        return this.states.equals(((DFAState)obj).states);
    }
}
