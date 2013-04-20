package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


public class TableWalkerNFA
{
    private NFA[] nfas;
    private List<String> input;

    public TableWalkerNFA(HashSet<NFA> nfas, List<String> input)
    {
    	Object[] temp = nfas.toArray();
        this.nfas = Arrays.copyOf(temp, temp.length, NFA[].class);
        this.input = input;
    }

    public List<Token> parse()
    {
    	System.out.println("Entered Parse");
        char curr;
        boolean hasAccept = false, failure = false;
        String lastKnown, identifier;
        int startPos = 0, index = 0;
        List<Token> output = new LinkedList<Token>();

        for (String str : input)
        {
            identifier = "";
            lastKnown = "";
            startPos= 0;
            index = 0;
            String currToken = "";
        	List<State> tempStates = new ArrayList<State>();
        	List<State> currStates = new ArrayList<State>();
        	List<State> tempStates2 = new ArrayList<State>();  
        	THISWHILE:
        	while(true){
	            NFAMAIN:
	            for(NFA n : nfas){
	            	tempStates2.add(n.getStart());
	            	UMM:
	            	while(index < str.length() && !failure){
	                    curr = str.charAt(index);
	                    if(curr == ' '){
	                    	for(State s : tempStates2){
	                    		if(s.getTransitionTable().keySet().contains("")){
	    	                		List<State> tempStates3 = noMoreEpsilons(s.getTransitionTable().get(""));
	    	                		for(State s1 : tempStates3){
	    	                			if(s1.isAccept()){
	    		                			System.out.println("Accepts: " + s1.getName());
	    		                            identifier = s1.getName();
	    		                            Token acc = new Token(identifier, currToken);
	    		                            output.add(acc);
	    		                            currToken = "";
	    		                            index++;
	    		                            break NFAMAIN;
	    		                		}
	    	                		}
	    	                	}
	                    		else if(s.isAccept()){
		                			System.out.println("Accepts: " + s.getName());
		                            identifier = s.getName();
		                            Token acc = new Token(identifier, currToken);
		                            output.add(acc);
		                            currToken = "";
		                            index++;
									tempStates2.clear();
									break NFAMAIN;
		                		}
	                    	}
	                    	continue UMM;
	                    }
	                    currToken += Character.toString(curr);
	            		currStates = tempStates2;
	            		RESET:
	                	for(State currState : currStates){
		                	if(currState.getTransitionTable().keySet().contains("")){
		                		tempStates = noMoreEpsilons(currState.getTransitionTable().get(""));
		                	}
		                	else{
		                		tempStates = new ArrayList<State>();
		                		tempStates.add(currState);
		                	}
		                	
		                	for(State s : tempStates){
		                		STUFF:
		                		if(s.isAccept()){
		                			for(State sta : tempStates){
			                			for(String reg : sta.getTransitionTable().keySet()){
			                				if(Pattern.matches(reg, Character.toString(curr))){
			                					break STUFF;
			                				}
		                				}
		                			}
		            				System.out.println("Accepts: " + s.getName());
		                            identifier = s.getName();
		                            Token acc = new Token(identifier, currToken);
		                            output.add(acc);
		                            currToken = "";
		                            break NFAMAIN;
		                        }
		                		System.out.println(s.getTransitionTable().size());
								for(String reg : s.getTransitionTable().keySet()){
									if(Pattern.matches(reg, Character.toString(curr))){
										tempStates2 = s.getTransitionTable().get(reg);
										index++;
										break RESET;
									}
								}
								currToken = "";
								tempStates2.clear();
								break UMM;
		                	}
	                	}
	                }
	            }
            }
        }
        return output;
    }
    
    private List<State> noMoreEpsilons(List<State> tempStates){
    	List<State> toRet = new ArrayList<State>();
    	for(State s : tempStates){
			if(s.getTransitionTable().keySet().contains("")){
				toRet.addAll(noMoreEpsilons(s.getTransitionTable().get("")));
			}
			else{
				toRet.add(s);
			}
		}
		return toRet;
    }
}
