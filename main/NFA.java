package main;

import java.util.ArrayList;
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
			case 1: return UNION(rexp1(), rexpP());
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
		n1.getAccept().setIsAccept(false);
		n1.getAccept().addTransition("", n2.getStart());
		n1.setAccept(n2.getAccept());
		return n1;
	}

	public NFA CLS_CHAR()
	{
		ArrayList<String> wat = new ArrayList<String>();
		for (char c=32; c<=126; c++) {
			if(c != '\\' && c != '^' && c != '-' && c != '[' && c != ']') {
				wat.add(Character.toString(c));
			}
		}
		String[] escapedChars = {"\\\\","\\^","\\-","\\[","\\]"};
		for(int i=0; i<escapedChars.length; i++)
		{
			wat.add(escapedChars[i]);
		}
		System.out.println(wat.toString());
		return null;
	}
	
	public NFA RE_CHAR()
	{
		ArrayList<String> wat = new ArrayList<String>();
		for (char c=32; c<=126; c++) {
			if(c != ' ' && c != '\\' && c != '*' && c != '+' && c != '?' && c != '|' && c != '[' &&
					c != ']' && c != '(' && c != ')' && c != '.' && c != '\'' && c != '"') {
				wat.add(Character.toString(c));
			}
		}
		String[] escapedChars = {"\\ ", "\\\\","\\*","\\+","\\?","\\|","\\[","\\]","\\(","\\)","\\.","\\\'","\\\""};
		for(int i=0; i<escapedChars.length; i++)
		{
			wat.add(escapedChars[i]);
		}
		System.out.println(wat.toString());
		return null;
	}
	
	public NFA UNION(NFA n1, NFA n2)
	{
		State newStart = new State(false, new HashMap<String, State>());
		newStart.addTransition("", n1.getAccept());
		newStart.addTransition("", n2.getAccept());
		State newAccept = new State(true, new HashMap<String, State>());
		n1.getAccept().setIsAccept(false);
		n2.getAccept().setIsAccept(false);
		n1.getAccept().addTransition("", newAccept);
		n2.getAccept().addTransition("", newAccept);
		n1.setAccept(newAccept);
		n2.setAccept(newAccept);
//		NFA uniNFA = new NFA("|");
//		State startS = new State(false, new HashMap<String, State>());
//		uniNFA.addTransition(startS, "|", new State(true, new HashMap<String, State>()));
//		uniNFA.setStart(startS);
		return n1;
	}
	
	public NFA epsilon()
	{
		NFA epsNFA = new NFA("EPSILON");
		State startS = new State(false, new HashMap<String, State>());
		epsNFA.addTransition(startS, "", new State(true, new HashMap<String, State>()));
		epsNFA.setStart(startS);
		return epsNFA;
	}
	
	public NFA star(NFA n)
	{
		State newStart = new State(false, new HashMap<String, State>());
		newStart.addTransition("", n.getAccept());
		n.getAccept().setIsAccept(false);
		n.getAccept().addTransition("", n.getStart());
		State newAccept = new State(true, new HashMap<String, State>());
		n.getAccept().addTransition("", newAccept);
		n.setAccept(newAccept);
		n.setStart(newStart);
//		NFA starNFA = new NFA("star");
//		State startS = new State(false, new HashMap<String, State>());
//		starNFA.addTransition(startS, "*", new State(true, new HashMap<String, State>()));
//		starNFA.setStart(startS);
		return n;
	}
	
	public NFA plus(NFA n)
	{
		State newStart = new State(false, new HashMap<String, State>());
		newStart.addTransition("", n.getStart());
		n.getAccept().setIsAccept(false);
		n.getAccept().addTransition("", n.getStart());
		State newAccept = new State(true, new HashMap<String, State>());
		n.getAccept().addTransition("", newAccept);
		n.setAccept(newAccept);
		n.setStart(newStart);
//		NFA plusNFA = new NFA("plus");
//		State startS = new State(false, new HashMap<String, State>());
//		plusNFA.addTransition(startS, "+", new State(true, new HashMap<String, State>()));
//		plusNFA.setStart(startS);
		return n;
	}
	
	public NFA dot()
	{
		NFA dotNFA = new NFA("dot");
		State startS = new State(false, new HashMap<String, State>());
		dotNFA.addTransition(startS, ".", new State(true, new HashMap<String, State>()));
		dotNFA.setStart(startS);
		return dotNFA;
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
