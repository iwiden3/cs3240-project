package tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import main.FileScanner;
import main.NFA;
import main.NFAFactory;

import org.junit.Before;
import org.junit.Test;

public class NFAFactoryTest {
	
	FileScanner fs;
	NFAFactory factory;
	HashSet<NFA> nfas;

	@Before
	public void setUp() throws Exception {
		fs = new FileScanner("tests/SampleSpec");
		factory = new NFAFactory(fs.getRegexTable(), fs.getTokenTable());
		nfas = factory.factorize();
	}

	@Test
	public void testSize() {
		assertEquals(8, nfas.size());
	}
	
//	@Test
//	public void testNFAs() {
//		for(NFA n : nfas)
//		{
//			System.out.println(n.toString());
//		}
//	}

}
