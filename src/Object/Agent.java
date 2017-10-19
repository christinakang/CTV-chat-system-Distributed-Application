package Object;

import ChatAppServerSide.AgentThread;

public class Agent extends Client{
	private String username;
	private String userID;
	private String line;
	public AgentThread aThread;
	private boolean avaliable;
	public Customer c1Ob= null;
	public Customer c2Ob= null;
	
	public Agent(Client client){
		this.username = client.getUsername();
		this.userID = client.getClientID();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public boolean isAvaliable() {
		checkAvaliable();
		return avaliable;
	}
    
	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}

	public void checkAvaliable(){
		if(c1Ob != null && c2Ob != null){
			setAvaliable(false);
		}
		else 
			setAvaliable(true);
	}
    
	
	
}
