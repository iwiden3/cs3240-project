package main;

import java.util.*;

public class State implements Comparable<State>
{
	private boolean accept;
	private HashMap<String, List<State>> transitionTable;
	private String name;
    private String id;
    // Needed for the creation of combo-states in the DFA
    private static int currId;

    static
    {
        currId = 1;
    }
	
	public State(boolean accept, HashMap<String, List<State>> table)
	{
		this.accept = accept;
		this.transitionTable = table;
        id = currId + "";
        currId++;
		name="";
	}
	
	public boolean isAccept()
	{
		return accept;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public HashMap<String, List<State>> getTransitionTable()
	{
		return transitionTable;
	}

    public void setIsAccept(boolean accept)
    {
        this.accept = accept;
    }

    public String getId()
    {
        return id;
    }
    
    public void addTransition(String trans, State toGo)
    {
    	if(transitionTable.containsKey(trans))
    	{
    		transitionTable.get(trans).add(toGo);
    	}
    	else
    	{
    		ArrayList<State> n = new ArrayList<State>();
    		n.add(toGo);
        	transitionTable.put(trans, n);
    	}
    }

    public int compareTo(State that)
    {
        int thisId = Integer.parseInt(this.id);
        int thatId = Integer.parseInt(that.id);

        return thisId - thatId;
    }
}
