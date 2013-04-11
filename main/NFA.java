package main;

import java.util.HashMap;
import java.util.Set;

public class NFA
{
	private State start;
    private List<State> accept;
	private String name;

	public NFA(String name, String def)
	{
        this.name = name;

        // Make accept state
        // TODO: this is for simple char classes only
        State accept = new State(true, new HashMap<String, State>());
        
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
	
	public NFA reg_ex()
	{
		return rexp();
	}
	
	public NFA rexp()
	{
		return concat(rexp1(), rexpP());
	}
	
	public NFA rexpP()
	{
		switch(x){
			case 1: return concat(UNION(), rexp1(), rexpP());
			case 2: return epsilon();
		}
	}
	
	public NFA rexp1()
	{
		return concat(rexp2(), rexp1P());
	}
	
	public NFA rexp1P()
	{
		switch(x){
			case 1: return concat(rexp2(), rexp1P());
			case 2: return epsilon();
		}
	}
	
	public NFA rexp2()
	{
		switch(x){
			case 1: return concat(rexp(), rexp2_tail());
			case 2: return concat(RE_CHAR(), rexp2_tail());
			case 3: return epsilon();
		}
	}
	
	public NFA rexp2_tail()
	{
		switch(x){
			case 1: return star();
			case 2: return plus();
			case 3: return epsilon();
		}
	}
	
	public NFA rexp3()
	{
		switch(x){
			case 1: return char_class();
			case 2: return epsilon();
		}
	}
	
	public NFA char_class()
	{
		switch(x){
			case 1: return dot();
			case 2: return openBracket + char_class1();
			case 3: return defined_class();
		}
	}
	
	public NFA char_class1()
	{
		switch(x){
			case 1: return char_set_list();
			case 2: return exclude_set();
		}
	}
	
	public NFA char_set_list()
	{
		switch(x){
			case 1: return concat(char_set(), char_set_list());
			case 2: return closeBracket;
		}
	}
	
	public NFA char_set()
	{
		return concat(CLS_CHAR(), char_set_tail());
	}
	
	public NFA char_set_tail()
	{
		switch(x){
			case 1: return CLS_CHAR();
			case 2: return epsilon();
		}
	}
	
	public NFA exclude_set()
	{
		return carrot + char_set() in exclude_set_tail();
	}
	
	public NFA exclude_set_tail()
	{
		return openBracket + char_set() + closeBracket;
		return defined_class();
	}
	
	public NFA concat(NFA n1, NFA n2)
	{
		return null;
	}
	
	public NFA concat(NFA n1, NFA n2, NFA n3)
	{
		return null;
	}
	
	public NFA CLS_CHAR()
	{
		
	}
	
	public NFA RE_CHAR()
	{
		
	}
	
	public NFA UNION()
	{
		
	}
	
	public NFA epsilon()
	{
		
	}
	
	public NFA star()
	{
		
	}
	
	public NFA plus()
	{
		
	}
	
	public NFA dot()
	{
		
	}

    public String getName()
    {
        return name;
    }

    public State getStart()
    {
        return start;
    }
    
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
