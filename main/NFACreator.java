package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFACreator {
	
	private NFA nfa;
	private int index;
	private String def;
	private ArrayList<String> splitDef;
	private HashSet<NFA> defined_classes;
	private HashMap<String, String> regexTable;
	
	public NFACreator(String name, String def, HashMap<String, String> regexTable, HashSet<NFA> regexNFAs)
	{
		index = 0;
		this.def = def;
		this.defined_classes = regexNFAs;
		this.regexTable = regexTable;
		splitDef = new ArrayList<String>();
		createSplitDef();
		nfa = reg_ex();
		nfa.setName(name);
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
		if(splitDef.get(index).equals("|"))
		{
			nextCurr();
			return UNION(rexp1(), rexpP());
		}
		else
		{
			return epsilonNFA();
		}
	}
	
	public NFA rexp1()
	{
		NFA rexp2 = rexp2();
		NFA rexp1P = rexp1P();
		if(rexp2 != null && rexp1P != null)
		{
			return concat(rexp2(), rexp1P());
		}
		else
		{
			return null;
		}
	}
	
	public NFA rexp1P()
	{
		NFA rexp2 = rexp2();
		NFA rexp1P = rexp1P();
		if(rexp2 != null && rexp1P != null)
		{
			return concat(rexp2(), rexp1P());
		}
		else
		{
			return epsilonNFA();
		}
	}
	
	public NFA rexp2()
	{
		if(splitDef.get(index).equals("("))
		{
			nextCurr();
			NFA rexp = rexp();
			if(rexp() != null)
			{
				NFA rexp2_tail = rexp2_tail(rexp);
				if(!rexp2_tail.getName().equals("EPSILON"))
				{
					return rexp2_tail;
				}
				else
				{
					return rexp;
				}
			}
		}
		
		if(RE_CHAR().contains(splitDef.get(index)))
		{
			NFA n = new NFA("YO", splitDef.get(index));
			nextCurr();
			NFA rexp2_tail = rexp2_tail(n);
			if(!rexp2_tail.getName().equals("EPSILON"))
			{
				return rexp2_tail;
			}
			else
			{
				return n;
			}
			
		}
		
		return rexp3();
	}
	
	public NFA rexp2_tail(NFA in)
	{
		switch(splitDef.get(index)){
			case "*": return star(in);
			case "+": return plus(in);
		}
		return epsilonNFA();
	}
	
	public NFA rexp3()
	{
		String char_class = char_class();
		if(char_class != null)
		{
			return new NFA("SUP", char_class);
		}
		else
		{
			return epsilonNFA();
		}
	}
	
	public String char_class()
	{
		if(splitDef.get(index).matches("[.]"))
		{
			nextCurr();
			return "[.]";
		}
		if(splitDef.get(index).equals("["))
		{
			nextCurr();
			String char_class1 = char_class1();
			if(char_class1 != null)
			{
				return "[" + char_class1();
			}
		}
		String defined_class = defined_class(splitDef.get(index));
		return defined_class; // null if nonexistent
	}
	
	public String char_class1()
	{
		String char_set_list = char_set_list();
		if(char_set_list != null)
		{
			return char_set_list();
		}
		String exclude_set = exclude_set();
		if(exclude_set != null)
		{
			return exclude_set();
		}
		return null;
	}
	
	public String char_set_list()
	{
		String char_set = char_set();
		if(char_set != null)
		{
			String char_set_list = char_set_list();
			if(char_set_list != null)
			{
				return char_set + char_set_list;
			}
		}
		else if(splitDef.get(index).equals("]"))
		{
			nextCurr();
			return "]";
		}
		return null;
	}
	
	public String char_set()
	{
		String ret = "";
		if(CLS_CHAR().contains(splitDef.get(index)))
		{
			ret += splitDef.get(index);
			nextCurr();
			String char_set_tail = char_set_tail();
			return ret + char_set_tail;
		}
		else
		{
			return null;
		}
	}
	
	public String char_set_tail()
	{
		String ret = "";
		if(splitDef.get(index).equals("-") && CLS_CHAR().contains(splitDef.get(index+1)))
		{
			ret += splitDef.get(index);
			nextCurr();
			ret += splitDef.get(index);
			nextCurr();
			return ret;
		}	
		else
		{
			return epsilon();
		}
	}
	
	public String exclude_set()
	{
		String ret = "";
		if(splitDef.get(index).equals("^"))
		{
			ret += splitDef.get(index);
			nextCurr();
			String char_set = char_set();
			if(char_set != null)
			{
				ret += char_set;
				if(splitDef.get(index).equals("]"))
				{
					ret += splitDef.get(index);
					nextCurr();
					if(splitDef.get(index).equals("I") && splitDef.get(index+1).equals("N"))
					{
						nextCurr();
						nextCurr();
						String exclude_set_tail = exclude_set_tail();
						if(exclude_set_tail != null)
						{
							ret = exclude_set_tail.substring(1,exclude_set_tail.length()-1) + ret;
							return ret;
						}
					}
				}
			}
		}	
		return null;
	}
	
	public String exclude_set_tail()
	{
		if(splitDef.get(index).equals("["))
		{
			nextCurr();
			String char_set = char_set();
			if(char_set != null && splitDef.get(index).equals("]"))
			{
				nextCurr();
				return "[" + char_set + "]";
			}
			return null;
		}
		
		String defined_class = defined_class(splitDef.get(index));
		return defined_class; // null if nonexistent
	}
	
	public String defined_class(String name)
	{
		String s = regexTable.get(name);
		if(s != null)
		{
			nextCurr();
		}
		return s;
	}
	
	public NFA defined_classNFA(String name)
	{
		for (NFA n : defined_classes)
		{
			if (n.getName().equals(name))
			{
				return n;
			}
		}
		return null;
	}
	
	public NFA concat(NFA n1, NFA n2)
	{
		if(n1 == null && n2 == null)
		{
			return null;
		}
		else if(n1 == null)
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
		State newStart = new State(false, new HashMap<String, List<State>>());
		newStart.addTransition("", n1.getAccept());
		newStart.addTransition("", n2.getAccept());
		State newAccept = new State(true, new HashMap<String, List<State>>());
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
	
	public NFA epsilonNFA()
	{
		NFA epsNFA = new NFA("EPSILON");
		State startS = new State(false, new HashMap<String, List<State>>());
		epsNFA.addTransition(startS, "", new State(true, new HashMap<String, List<State>>()));
		epsNFA.setStart(startS);
		return epsNFA;
	}
	
	public String epsilon()
	{
		return "";
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
	
	public void createSplitDef()
	{
		String curr;
		for(int i=0; i<def.length(); i++)
		{
			curr = def.substring(i, i+1);
			if(curr.equals(" "))
			{
				continue;
			}
			if(curr.equals("\\"))
			{
				i++;
				curr += def.substring(i, i+1);
			}
			else if(curr.equals("$") && def.substring(i+1, i+2).matches("[a-zA-Z]"))
			{
				while(def.substring(i+1, i+2).matches("[a-zA-Z]"))
				{
					i++;
					curr += def.substring(i, i+1);
					if(i >= def.length()-1)
					{
						break;
					}
				}
			}
			splitDef.add(curr);
		}
		//System.out.println(splitDef.toString());
	}
	
	public void nextCurr()
	{
		index++;		
	}
	
	public void goBack()
	{
		index--;
	}
}