package ChatAppServerSide;
import java.io.*;
import java.net.Socket;
import java.util.*;
import Object.Client;

public class LoginCheckThread extends Thread{
	Client client = new Client();
	Socket clientSocket;
	BufferedReader fromClient;
	PrintWriter toClient;
	Scanner input;
     /*
      * The log in check will keep checking the user until got right result 
      */
	public LoginCheckThread(Socket socket) {		
		this.clientSocket = socket;
		try {
			fromClient=  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
			toClient = new PrintWriter(clientSocket.getOutputStream(),true);
			 boolean check = false;
			
			while(!check)
			{
				String loginDetails = fromClient.readLine();
				//login
				client = new Client(loginDetails);
				checkClient();
				if(!client.isCheck())
				{
					toClient.println("False");
				}
				else{
					check = true;
					toClient.println(client.getRole());
					
				}
			}
			
		} catch (Exception e) {
			System.out.println("Exception from construction log in "+ e);
		}
        
	}
	
	/*this function check if the client exist in our file system
	 * if yes retrieve more details about the client
	 * else set the check to false 
	 */
	public void checkClient(){
		try {
			try{
			File file = new File("Client.txt");
		    	
			input = new Scanner(file);
		
		while(input.hasNext()) {
			String line = input.nextLine();
			String[] section = line.split(";");
			
			if(section[1].equals(client.getUsername())){
				//System.out.println("Client exsist username correct");
				if(section[2].equals(client.getPassword())){
					//System.out.println("username and password correct");
					client.setClientID(section[0]);
					setClientRole();
					client.setCheck(true);
					break;
				}else{
					client.setCheck(false);
				}
			}
			else{
				client.setCheck(false);
			}
			
		}// end while
		}finally{
			input.close();
		}
		}catch (Exception e) {
			System.out.println("Exception from check client "+ e);
		}
		
	}
	
	/*
	 * After the client was found in the file, the role will be set to the object
	 */
	public void setClientRole(){
		if(client.getClientID().startsWith("Agent")){
			client.setRole("Agent");
			System.out.println("Role is"+ client.getRole());
		}//end agent if
		
		else if(client.getClientID().startsWith("Customer")) {
			client.setRole("Customer");
			System.out.println("Role is"+ client.getRole());
		}//end customer if
	}
	
}
