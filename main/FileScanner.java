//scanner generator
//read lexical specifications
//generate primitive NFAs
package main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class FileScanner{
    //hand banana NOOOOOOOOOOOO

    private HashMap<String,String> regexTable = new HashMap<String,String>();
    private HashMap<String,String> tokenTable = new HashMap<String,String>();
    private final static Charset ENCODING = StandardCharsets.US_ASCII;

    public FileScanner(String file) throws IOException{
        List<String> text = readTextFile(file);
        HashMap<String,String> tempTable = regexTable;
        
        for(int i=0; i<text.size(); i++){
            String currLine = text.get(i);
            String[] splitString = (currLine.split(" "));
            if(splitString.length < 2){
            	tempTable = tokenTable;
            }
            else{
	            String value = splitString[1];
	            for(int j=2; j<splitString.length; j++){
	            	if(splitString[j].equalsIgnoreCase("in")){
	            		String tempVal = tempTable.get(splitString[j+1]); //Retrieves the regex for "in $DIGIT/$CHAR"
	            		value = tempVal.substring(0,tempVal.length()-1) + value.substring(1); //Appends the previous regex with the new one
	            		break;
	            	}
	            	else{
	            		value = value + " " + splitString[j];
	            	}
	            }
	            //System.out.println(splitString[0]);
	            //System.out.println(value);
	            tempTable.put(splitString[0],value);
            }
        }
    }

    private List<String> readTextFile(String aFileName) throws IOException{
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }
    
    public HashMap<String,String> getRegexTable(){
        return regexTable;
    }
    
    public HashMap<String,String> getTokenTable(){
        return tokenTable;
    }
}




 