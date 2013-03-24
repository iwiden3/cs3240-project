import java.util.*;


public class state
{
	private boolean accept;
	private HashMap<String,String> transitionTable;
	
	public state(boolean accept,HashMap<String,String> table)
	{
		this.accept=accept;
		this.transitionTable=table;
	}
	
	public boolean isAccept()
	{
		return accept;
	}
	
	public HashMap<String,String> getTransitionTable()
	{
		return transitionTable;
	}
}

