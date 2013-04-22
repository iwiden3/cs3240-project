package main;

import java.io.IOException;

public class HomeStarRunner {

	public static void main(String[]args) throws IOException
	{		
		DriverNFA dr = new DriverNFA("tests/input1", "tests/spec1");
		dr.start();
	}
}