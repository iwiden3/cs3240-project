package main;

import java.util.*;

public class State
{
	private boolean accept;
	private HashMap<String, State> transitionTable;
	
	public State(boolean accept,HashMap<String, State> table)
	{
		this.accept = accept;
		this.transitionTable = table;
	}
	
	public boolean isAccept()
	{
		return accept;
	}
	
	public HashMap<String, State> getTransitionTable()
	{
		return transitionTable;
	}
}

