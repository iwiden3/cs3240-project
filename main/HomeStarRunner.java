package main;

import java.io.IOException;
import java.util.*;

public class HomeStarRunner {

	public static void main(String[]args) throws IOException
	{
		FileScanner fs;
		NFAFactory factory;
		HashSet<NFA> nfas;
		
		fs = new FileScanner("tests/SampleSpec");
		factory = new NFAFactory(fs.getRegexTable(), fs.getTokenTable());
		nfas = factory.factorize();
		
		for(NFA n: nfas)
		{
			System.out.println(n.getName());
			System.out.println(n.toListStrings());
		}
		
		Driver dr2=new Driver("tests/SampleSpec");
		
		System.out.println(dr2.whatType("a"));
		
		
		
		/*
		ArrayList<String> list=new ArrayList<String>();
		list.add("a = 5");
		list.add("PRINT a");
		list.add("b = a * 10 + 20");
		list.add("PRINT b");
		
		Driver dr=new Driver(list,"tests/SampleSpec");*/
		
		
		
	}
}
