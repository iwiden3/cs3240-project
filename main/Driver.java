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
		Name name=whatType(in,nfa.getStart(),0);
		return name.getName();
		
	}
	
	
	public Name whatType(String in, State st,int num)
	{
		Name out=new Name(st.getName(),num);
		
		HashMap<String, List<State>> map=st.getTransitionTable();
		HashSet<String> keys=(HashSet<String>) st.getTransitionTable().keySet();
		HashSet<State> states =new HashSet<State>();
		for(String str: keys)
		{
			if(str.isEmpty() || in.matches(str))
			{
				for(State st2 : st.getTransitionTable().get(str))
				{
					states.add(st2);
				}
				
			}
		}

		if(states.isEmpty())
		{
			return new Name("",num);
		}
		else if(map.containsKey("") && states.size()==2)
		{
			return new Name(st.getName(),num);
		}
		else
		{
			for (State st2 : states)
			{
				Name curr=whatType(in, st2,num++);
				if(curr.getNum()>=num && !curr.getName().isEmpty())
				{
					out.setName(curr.getName());
					out.setNum(curr.getNum());
				}
			}
			return out;
		}	
	}
}
	
