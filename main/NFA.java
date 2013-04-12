package main;

import java.util.*;


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
	
	public void setName(String name)
	{
		this.name = name;
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
    public List<String> toListStrings()
    {
    	ArrayList<String> fin=new ArrayList<String>();
    	HashMap<String,List<State>> map=start.getTransitionTable();
    	Set<String> s=map.keySet();
    	
    	HashMap<String,List<State>> map2=accept.getTransitionTable();
    	Set<String> s2=map.keySet();
    	fin.add("Start");
    	for(String k : s)
    	{
    		fin.add(k);
    		List<State> states= map.get(k);
    		if(states!=null)
    		{	
    			for(State paul: states)
    			{
    				Set<String> stuff=paul.getTransitionTable().keySet();
    				for(String l : stuff)
    				{
    					fin.add(l);
    				}
    			}
    		}
    	}
    	
    	fin.add("Accept");
    	
    	for(String k : s2)
    	{
    		fin.add(k);
    		List<State> states= map2.get(k);
    		if(states!=null)
    		{	
    			for(State kurt: states)
    			{
    				Set<String> stuff=kurt.getTransitionTable().keySet();
    				for(String l : stuff)
    				{
    					fin.add(l);
    				}
    			}	
    	    }
    	}
    	
    	return fin;
    }
}
