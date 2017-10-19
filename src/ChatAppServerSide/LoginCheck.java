package ChatAppServerSide;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Object.*;

public class LoginCheck implements Runnable{
	private Client client;
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LoginCheck(Client client) {		
		this.client = client;
	}
	
	public void run(){
		try {
			checkClient(client);
			System.out.println("check is "+ client.isCheck());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void checkClient(Client client) throws FileNotFoundException {
		File file = new File("Client.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			String line = input.nextLine();
			String[] section = line.split(";");
			/*for(int i = 0; i< section.length; i++){
				System.out.println("Section i is "+ section[i]);
			}*/
			
			if(section[1].equals(client.getUsername())){
				//System.out.println("Client exsist username correct");
				if(section[2].equals(client.getPassword())){
					//System.out.println("username and password correct");
					client.setClientID(section[0]);
					setClientRole(client);
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
		input.close();
	}
	
	public void setClientRole(Client client){
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
