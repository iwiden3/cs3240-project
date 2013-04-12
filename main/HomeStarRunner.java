package main;

import java.io.IOException;
import java.util.*;

public class HomeStarRunner {

	public static void main(String[]args) throws IOException
	{
//		FileScanner fs;
//		NFAFactory factory;
//		HashSet<NFA> nfas;
//		
//		fs = new FileScanner("tests/SampleSpec");
//		factory = new NFAFactory(fs.getRegexTable(), fs.getTokenTable());
//		nfas = factory.factorize();
//		
//		for(NFA n: nfas)
//		{
//			System.out.println(n.getName());
//			System.out.println(n.toListStrings());
//		}
		
		HashSet<Integer> test=new HashSet<Integer>();
		Driver dr2=new Driver("tests/SampleSpec");
		String str=dr2.whatType("a");
		test.add(str.compareTo("$IDENTIFIER"));
		System.out.println(str);

		str=dr2.whatType("=");
		System.out.println(str);

		str=dr2.whatType("+");
		test.add(str.compareTo("$PLUS"));
		str=dr2.whatType("-");
		test.add(str.compareTo("$MINUS"));
		str=dr2.whatType("*");
		test.add(str.compareTo("$MULTIPLY"));
		str=dr2.whatType("PRINT");
		
		//ArrayList<String> list=new ArrayList<String>();
		//list.add("a = 5");
		//list.add("PRINT a");
		//list.add("b = a * 10 + 20");
		//list.add("PRINT b");
		
		//Driver dr=new Driver(list,"tests/SampleSpec");	
	}
}