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
		factory = new NFAFactory(fs.getRegexTable());
		nfas = factory.factorize();
	}

	@Test
	public void testSize() {
		assertEquals(5, nfas.size());
	}
	
	@Test
	public void testNFAs() {
		for(NFA n : nfas)
		{
			n.CLS_CHAR();
			System.out.println(n.toString());
		}
	}

}
