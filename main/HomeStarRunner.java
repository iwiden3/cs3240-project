package main;

import java.io.IOException;
import java.util.HashSet;

public class HomeStarRunner {

	public static void main(String[]args) throws IOException
	{
		FileScanner fs;
		NFAFactory factory;
		HashSet<NFA> nfas;
		
		fs = new FileScanner("tests/SampleSpec");
		factory = new NFAFactory(fs.getRegexTable(), fs.getTokenTable());
		nfas = factory.factorize();
		System.out.println(nfas.size());
	}
}
