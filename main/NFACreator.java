package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class NFACreator {
	
	private NFA nfa;
	private int index;
	private String def;
	private ArrayList<String> splitDef;
	private HashSet<NFA> defined_classes;
	private HashMap<String, String> regexTable;
	private Stack<Integer> myStack = new Stack<Integer>();
	
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
		myStack.push(index);
		NFA rexp1 = rexp1();
		if(rexp1 != null)
		{
			NFA rexpP = rexpP(rexp1);
			if(!rexpP.getName().equals("EPSILON"))
			{
				myStack.pop();
				return rexpP;
			}
			else
			{
				myStack.pop();
				return rexp1;
			}
		}
		index = myStack.pop();
		return null;
	}
	
	public NFA rexpP(NFA in)
	{
		if(index >= splitDef.size())
		{
			return epsilonNFA();
		}
		myStack.push(index);
		if(splitDef.get(index).equals("|"))
		{
			nextCurr();
			NFA rexp1 = rexp1();
			if(rexp1 != null)
			{
				NFA temp = UNION(in, rexp1);
				NFA toRet = rexpP(temp);
				myStack.pop();
				return toRet;
			}
			else
			{
				index = myStack.pop();
				return epsilonNFA();
			}
		}
		else
		{
			index = myStack.pop();
			return in;
		}
	}
	
	public NFA rexp1()
	{
		myStack.push(index);
		NFA rexp2 = rexp2();
		NFA rexp1P = rexp1P();
		if(rexp2 != null && rexp1P != null)
		{
			myStack.pop();
			return concat(rexp2, rexp1P);
		}
		else
		{
			index = myStack.pop();
			return null;
		}
	}
	
	public NFA rexp1P()
	{
		if(index >= splitDef.size())
		{
			return epsilonNFA();
		}
		myStack.push(index);
		NFA rexp2 = rexp2();
		NFA rexp1P = rexp1P();
		if(rexp2 != null && rexp1P != null)
		{
			myStack.pop();
			return concat(rexp2, rexp1P);
		}
		else
		{
			index = myStack.pop();
			return epsilonNFA();
		}
	}
	
	public NFA rexp2()
	{
		myStack.push(index);
		if(splitDef.get(index).equals("("))
		{
			nextCurr();
			NFA rexp = rexp();
			if(rexp() != null)
			{
				if(splitDef.get(index).equals(")"))
				{
					nextCurr();
					NFA rexp2_tail = rexp2_tail(rexp);
					if(!rexp2_tail.getName().equals("EPSILON"))
					{
						myStack.pop();
						return rexp2_tail;
					}
					else
					{
						myStack.pop();
						return rexp;
					}
				}
			}
		}
		
		index = myStack.pop();
		myStack.push(index);
		
		if(RE_CHAR().contains(splitDef.get(index)))
		{
			NFA n = new NFA("YO", splitDef.get(index));
			nextCurr();
			NFA rexp2_tail = rexp2_tail(n);
			myStack.pop();
			return rexp2_tail;
		}
		
		index = myStack.pop();
		myStack.push(index);
		
		NFA rexp3 = rexp3();
		if(rexp3 != null)
		{
			myStack.pop();
			return rexp3;
		}
		
		index = myStack.pop();
		return null;
	}
	
	public NFA rexp2_tail(NFA in)
	{
		if(index >= splitDef.size())
		{
			return epsilonNFA();
		}
		myStack.push(index);
		switch(splitDef.get(index)){
			case "*": 
				nextCurr();
				myStack.pop();
				return star(in);
			case "+": 
				nextCurr();
				myStack.pop();
				return plus(in);
		}
		
		index = myStack.pop();
		return in;
	}
	
	public NFA rexp3()
	{
		if(index >= splitDef.size())
		{
			return epsilonNFA();
		}
		myStack.push(index);
		String char_class = char_class();
		if(char_class != null)
		{
			myStack.pop();
			return new NFA("SUP", char_class);
		}
		else
		{
			return epsilonNFA();
		}
	}
	
	public String char_class()
	{
		myStack.push(index);
		if(splitDef.get(index).matches("[.]"))
		{
			nextCurr();
			myStack.pop();
			return "[.]";
		}
		index = myStack.pop();
		myStack.push(index);
		if(splitDef.get(index).equals("["))
		{
			nextCurr();
			String char_class1 = char_class1();
			if(char_class1 != null)
			{
				myStack.pop();
				return "[" + char_class1;
			}
		}
		index = myStack.pop();
		myStack.push(index);
		String defined_class = defined_class(splitDef.get(index));
		if(defined_class == null)
		{
			index = myStack.pop();
		}
		else
		{
			myStack.pop();
		}
		return defined_class; // null if nonexistent
	}
	
	public String char_class1()
	{
		myStack.push(index);
		String char_set_list = char_set_list();
		if(char_set_list != null)
		{
			myStack.pop();
			return char_set_list;
		}
		index = myStack.pop();
		myStack.push(index);
		String exclude_set = exclude_set();
		if(exclude_set != null)
		{
			myStack.pop();
			return exclude_set;
		}
		index = myStack.pop();
		return null;
	}
	
	public String char_set_list()
	{
		myStack.push(index);
		String char_set = char_set();
		if(char_set != null)
		{
			String char_set_list = char_set_list();
			if(char_set_list != null)
			{
				myStack.pop();
				return char_set + char_set_list;
			}
		}
		index = myStack.pop();
		myStack.push(index);
		if(splitDef.get(index).equals("]"))
		{
			nextCurr();
			myStack.pop();
			return "]";
		}
		index = myStack.pop();
		return null;
	}
	
	public String char_set()
	{
		myStack.push(index);
		String ret = "";
		if(CLS_CHAR().contains(splitDef.get(index)))
		{
			ret += splitDef.get(index);
			nextCurr();
			String char_set_tail = char_set_tail();
			myStack.pop();
			return ret + char_set_tail;
		}
		else
		{
			index = myStack.pop();
			return null;
		}
	}
	
	public String char_set_tail()
	{
		if(index >= splitDef.size())
		{
			return epsilon();
		}
		myStack.push(index);
		String ret = "";
		if(splitDef.get(index).equals("-") && CLS_CHAR().contains(splitDef.get(index+1)))
		{
			ret += splitDef.get(index);
			nextCurr();
			ret += splitDef.get(index);
			nextCurr();
			myStack.pop();
			return ret;
		}	
		else
		{
			myStack.pop();
			return epsilon();
		}
	}
	
	public String exclude_set()
	{
		myStack.push(index);
		String ret = "";
		System.out.println(index);
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
							myStack.pop();
							return ret;
						}
					}
				}
			}
		}
		index = myStack.pop();
		return null;
	}
	
	public String exclude_set_tail()
	{
		myStack.push(index);
		if(splitDef.get(index).equals("["))
		{
			nextCurr();
			String char_set = char_set();
			if(char_set != null && splitDef.get(index).equals("]"))
			{
				nextCurr();
				myStack.pop();
				return "[" + char_set + "]";
			}
		}
		index = myStack.pop();
		myStack.push(index);
		String defined_class = defined_class(splitDef.get(index));
		if(defined_class == null)
		{
			index = myStack.pop();
		}
		else
		{
			myStack.pop();
		}
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
		//System.out.println(wat.toString());
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
		//System.out.println(wat.toString());
		return wat;
	}
	
	public NFA UNION(NFA n1, NFA n2)
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
		n1.setStart(newStart);
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
		State acceptS = new State(true, new HashMap<String, List<State>>());
		epsNFA.addTransition(startS, "", acceptS);
		epsNFA.setStart(startS);
		epsNFA.setAccept(acceptS);
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
	
//	public NFA dot()
//	{
//		NFA dotNFA = new NFA("dot");
//		State startS = new State(false, new HashMap<String, List<State>>());
//		dotNFA.addTransition(startS, ".", new State(true, new HashMap<String, List<State>>()));
//		dotNFA.setStart(startS);
//		return dotNFA;
//	}
//	
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
		if(index >= splitDef.size())
		{
			
		}
	}
}