package Object;

import ChatAppServerSide.CustomerThread;

public class Customer extends Client{
	private String username;	
	private String userID;
	private String line;
	public CustomerThread cThread;
	public Agent agentOb = null;
	
	public Customer(Client c){
		this.username = c.getUsername();
		this.userID = c.getClientID();
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
	
	
	
}
