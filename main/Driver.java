package main;
import java.util.*;

public class Driver
{
	NFA nfa;
	public Driver()
	{
		
	
	}
	
	public String whatType(String in)
	{
		boolean found=false;
		State curr=nfa.getStart();
		while(!found)
		{
			HashSet<String> keys=(HashSet<String>) curr.getTransitionTable().keySet();
			HashSet<State> states =new HashSet<State>();
			for(String str: keys)
			{
				if(str.isEmpty() || in.matches(str))
				{
					for(State st : curr.getTransitionTable().get(str))
					{
						states.add(st);
					}
					
				}
			}
			
		}
		return null;
	}
	
	private boolean whatType(State st)
	{
		if(st.isAccept())
		{
			return true;
		}
		else
		{
			HashSet<String> keys=(HashSet<String>) st.getTransitionTable().keySet();
			for(String str : keys)
			{
				for(State crap : st.getTransitionTable().get(str))
				{
					return whatType(crap)
					
				}
			}
		}
		return false;
	}
}
	
