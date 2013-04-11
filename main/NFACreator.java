package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NFACreator {
	
	private NFA nfa;
	private int index;
	private String def;
	
	public NFACreator(String name, String def)
	{
		nfa = new NFA(name);
		index = 0;
		this.def = def;
	}
	
	public NFA getNFA()
	{
		return nfa;
	}

	public NFA reg_ex()
	{
		return rexp();
	}
	
	public NFA rexp()
	{
		return UNION(rexp1(), rexpP());
	}
	
	public NFA rexpP()
	{
		String x;
		try
		{
			x = def.substring(index, index+1);
		}
		catch(IndexOutOfBoundsException e)
		{
			//BLEHHHHH NOPE FIX THIS SHIT BREAK OUT OF EVERYTHING
			return null;
		}
		switch(x){
			case "|":
				index++;
				return UNION(rexp1(), rexpP());
			default:
				return epsilon();
		}
	}
	
	public NFA rexp1()
	{
		return concat(rexp2(), rexp1P());
	}
	
	public NFA rexp1P()
	{
		String x;
		try
		{
			x = def.substring(index, index+1);
		}
		catch(IndexOutOfBoundsException e)
		{
			//BLEHHHHH NOPE FIX THIS SHIT
			return null;
		}
		switch(x){
			case "": return epsilon();
			default: return concat(rexp2(), rexp1P());
		}
	}
	
	public NFA rexp2()
	{
		switch(x){
			case "(": return concat(rexp(), rexp2_tail());
			case "": return epsilon();
			default: return concat(RE_CHAR(), rexp2_tail());
		}
	}
	
	public NFA rexp2_tail()
	{
		switch(x){
			case "*": return star();
			case "+": return plus();
			case "": return epsilon();
		}
	}
	
	public NFA rexp3()
	{
		switch(x){
			case "": return epsilon();
			default: return char_class();
		}
	}
	
	public NFA char_class()
	{
		switch(x){
			case '.': return dot();
			case '[': return openBracket + char_class1();
			case '$': return defined_class();
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
		if(CLS_CHAR().contains(def.substring(index,index+1)))
		{
			index++; // Create NFA with the char
			return concat(newNFA, char_set_tail());
		}
		else
		{
			index--;
			return null;
		}
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
		if(n1 == null)
		{
			return n2;
		}
		else if(n2 == null)
		{
			return n1;
		}
		n1.getAccept().setIsAccept(false);
		n1.getAccept().addTransition("", n2.getStart());
		n1.setAccept(n2.getAccept());
		return n1;
	}

	public ArrayList<String> CLS_CHAR()
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
		return wat;
	}
	
	public ArrayList<String> RE_CHAR()
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
		return wat;
	}
	
	public NFA UNION(NFA n1, NFA n2)
	{
		if(n1 == null)
		{
			return n2;
		}
		else if(n2 == null)
		{
			return n1;
		}
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
		State startS = new State(false, new HashMap<String, List<State>>());
		epsNFA.addTransition(startS, "", new State(true, new HashMap<String, List<State>>()));
		epsNFA.setStart(startS);
		return epsNFA;
	}
	
	public NFA star(NFA n)
	{
		State newStart = new State(false, new HashMap<String, List<State>>());
		newStart.addTransition("", n.getAccept());
		n.getAccept().setIsAccept(false);
		n.getAccept().addTransition("", n.getStart());
		State newAccept = new State(true, new HashMap<String, List<State>>());
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
		State newStart = new State(false, new HashMap<String, List<State>>());
		newStart.addTransition("", n.getStart());
		n.getAccept().setIsAccept(false);
		n.getAccept().addTransition("", n.getStart());
		State newAccept = new State(true, new HashMap<String, List<State>>());
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
		State startS = new State(false, new HashMap<String, List<State>>());
		dotNFA.addTransition(startS, ".", new State(true, new HashMap<String, List<State>>()));
		dotNFA.setStart(startS);
		return dotNFA;
	}
}
