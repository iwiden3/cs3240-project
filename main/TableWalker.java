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

    public TableWalker(DFA dfa)
    {
        this.dfa = dfa; 
    }
}
