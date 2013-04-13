package main;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;


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
        DFAState currState;
        boolean hasAccept = false, failure = false;
        String lastKnown, identifier;
        int startPos = 0, endPos = 0, index = 0;
        List<Token> output = new LinkedList<Token>();

        for (String s : input)
        {
            currState = dfa.getStart();
            identifier = "";
            lastKnown = "";
            hasAccept = false;
            startPos= 0;
            endPos = 0;
            index = 0;

            while (index < s.length() && !failure)
            {
                curr = s.charAt(index);

                for (String reg : currState.getTransitions().keySet())
                {
                   if (Pattern.matches(reg, Character.toString(curr)))
                   {
                       currState = currState.getTransitions().get(reg);
                       if (currState.isAccept())
                       {
                           hasAccept = true;
                           endPos = index;
                           identifier = currState.getName();
                           Token acc = new Token(identifier, s.substring(startPos, endPos));
                           output.add(acc);
                       }
                       break;
                   }
                   else
                   {
                       failure = true;
                   }
                }
                index++;
            }
        }
        
        return output;
    }
}
