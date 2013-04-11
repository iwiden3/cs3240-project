package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class NFA
{
	private State start;
    private State accept;
	private String name;
	
	public NFA(String name)
	{
		this.name = name;
	}

	public NFA(String name, String def)
	{
        this.name = name;

        // Make accept state
        // TODO: this is for simple char classes only
        State accept = new State(true, new HashMap<String, List<State>>());
        
        this.accept = accept;
        
        // Create transition table
        HashMap<String, List<State>> transition = new HashMap<String, List<State>>();
        transition.put(def, new ArrayList<State>());
        transition.get(def).add(accept);
        
        // Create start state
        if (!def.equals(""))
        {
            start = new State(false, transition);
        }
        else
        {
            start = new State(true, transition);
        }
	}

	public String getName()
    {
        return name;
    }

    public State getStart()
    {
        return start;
    }	

	public void setStart(State state)
	{
		this.start = state;	
	}
    
    public State getAccept()
    {
        return accept;
    }
    
    public void setAccept(State state)
    {
    	this.accept = state;
    }
    
    public void addTransition(State s1, String trans, State s2)
    {
    	s1.addTransition(trans, s2);
    }
    
    // Meh I think this broke but whatever
    public String toString()
    {
    	String toReturn = name;
    	HashMap<String, List<State>> nextStates = start.getTransitionTable();
    	Set<String> nextKeys = nextStates.keySet();
    	for(String key : nextKeys)
    	{
    		toReturn += (" -- " + key + " --> ");
    		if(nextStates.get(key).get(0).isAccept())
    		{
    			toReturn += "ACCEPT";
    		}
    		else
    		{
    			toReturn += "NODE";
    		}
    	}
    	return toReturn;
    }
}
