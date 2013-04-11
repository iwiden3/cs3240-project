package main;

import java.util.HashMap;
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
        State accept = new State(true, new HashMap<String, State>());
        
        this.accept = accept;
        
        // Create transition table
        HashMap<String, State> transition = new HashMap<String, State>();
        transition.put(def, accept);
        
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
    	HashMap<String, State> nextStates = start.getTransitionTable();
    	Set<String> nextKeys = nextStates.keySet();
    	for(String key : nextKeys)
    	{
    		toReturn += (" -- " + key + " --> ");
    		if(nextStates.get(key).isAccept())
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
