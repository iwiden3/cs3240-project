package main;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TableWalker
{
    private DFA dfa;
    private List<String> input;

    public TableWalker(DFA dfa, List<String> input)
    {
        this.dfa = dfa; 
        this.input = input;
    }

    public List<Token> parse()
    {
        char curr;
        boolean hasAccept;
        String lastKnown;
        int startPos = 0, endPos = 0;
        List<Token> output = new LinkedList<Token>();

        for (String s : input)
        {
            lastKnown = "";
            hasAccept = false;
            startPos= 0;
            endPos = 0;

        }
        
        return output;
    }
    
    private Token tokChar()
}
