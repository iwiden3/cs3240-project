package main;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Driver
{
	NFA nfa;
	public Driver( NFA nfa)
	{
		this.nfa=nfa;
	}
	
	public String whatType(String in)
	{
		Name name=whatType(in,nfa.getStart(),0);
		return name.getName();
	}
	
	public void readInput(String file) throws IOException
	{
		List<String> text = readTextFile(file);
		ArrayList<String> out=new ArrayList<String>();
		for(String s: text)
		{
			String[] splitString = (s.split(" "));
			for(int i=0;i<splitString.length;i++)
			{
				String type=whatType(splitString[i]);
				String fin=type + " "+ splitString[i];
				out.add(fin);
			}
		}
	
		FileWriter writer = new FileWriter("output.txt"); 
		for(String str: out) 
		{
		  writer.write(str);
		}
		writer.close();
	}
	
	
	
	 private List<String> readTextFile(String aFileName) throws IOException{
	        Path path = Paths.get(aFileName);
	        return Files.readAllLines(path, ENCODING);
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
	
