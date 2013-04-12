package main;

import java.util.*;

public class DFAState implements Comparable<DFAState>
{
    private String name;
    private String id;
    private boolean accept;
    private HashMap<String, State> transitionTable;

    public DFAState(String name, String id, boolean accept,
            HashMap<String, State> transitionTable)
    {
        this.name = name;
        this.id = id;
        this.accept = accept;
        this.transitionTable = transitionTable;
    }

    public boolean isAccept()
    {
        return accept;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public HashMap<String, State> getTransitions()
    {
        return transitionTable;
    }

    public int compareTo(DFAState that)
    {
        return 0;
    }
}
