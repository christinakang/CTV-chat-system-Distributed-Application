package Object;

/** 
 * 
 * @author Ong Jun Quan
 *
 */

/**
 * This class is used to store a client's detail
 */
public class Client{
	private String clientID;
	private boolean check;
	private String username;
	private String password;
	private String role;

	
	public Client(){
		
	}
	public Client(String loginDetails) {
		String[] tmp = loginDetails.split(";");
		this.username = tmp[0];
		this.password = tmp[1];
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClientID() {
		return clientID;
	}
	
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role){
		this.role = role;
	}
	
	
	 public boolean isCheck() {
			return check;
		}
		public void setCheck(boolean check) {
			this.check = check;
		}

	
}

