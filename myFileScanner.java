//scanner generator
//read lexical specifications
//generate primitive NFAs

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public myFileScanner(){
    //hand banana NOOOOOOOOOOOO

    private HashMap<String,String> myTable = new HashMap<String,String>();
    private final static Charset ENCODING = StandardCharsets.US_ASCII;

    public myFileScanner(String file){
        List<String> text = readTextFile(file);
        
        for(int i=0; i<text.size(); i++){
            String currLine = text.get(i);
            String[] splitString = (text.split(" "));
            String value = splitString[1];
            for(int j=2; j<splitString.size(); j++){
                value = value + " " + splitString[i];
            }
            myTable.put(splitString[0],value);
        }
    }

    private List<String> readTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }
    
    public HashMap<String,String> getTable(){
        return myTable;
    }
}




 