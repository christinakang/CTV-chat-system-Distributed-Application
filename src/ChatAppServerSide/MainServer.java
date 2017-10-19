package ChatAppServerSide;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import Object.*;


public class MainServer {
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	int portNumber = 8080;
    static int Queue_cap = 10;
    static ArrayList<Customer> customerobs = new ArrayList<Customer>();
    static ArrayList<Agent> agentobs = new ArrayList<Agent>();
    static ArrayBlockingQueue<Thread> connectionQueue = new ArrayBlockingQueue<Thread>(Queue_cap);
    static Client clientob = null;

    
	  public static void main(String args[]) throws Exception {
	    new MainServer().createserver();
	  }
	  
	  public void createserver() throws Exception
	  {   
	    try {
	      serverSocket = new ServerSocket(portNumber);
	      System.out.println("Server is running");    
	    } catch (IOException e) {
	      System.out.println(e);
	    }
	  
	    try{
	    	try{  		
	    		while(true)
	    		{
		    	    clientSocket = serverSocket.accept();
					PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);
					toClient.println("Connecting");
					//Receiving or reading data from the socket, reading the client role 
					LoginCheckThread login = new LoginCheckThread(clientSocket);					
					clientob = login.client;
					
					
					/*
					 * check if the role is customer
					 * if yes will create the customer thread 
					 */
					if(clientob.getRole().equals("Customer")){
				        
						Customer customerob = new Customer(clientob);						
						customerob.cThread = new CustomerThread(clientSocket,customerob);
						customerobs.add(customerob);
						
						
						boolean check = false;
						/*
						 * this while loop will first put the thread in the queue
						 * then will check if any agent avalaibe by calling the checkAgentAvaliable funtion 
						 * if avalabile it will take that thread from queue and run 
						 * else need to wait and retry 
						 */
						while(!check){
							connectionQueue.put(customerob.cThread);
							//check if got agent available
							if(checkAgentAvaliable(customerob)== true){
								String tmp = "true";
								System.out.println(tmp);
								toClient.println(tmp);
								toClient.println(clientob.getClientID()+";"+"Welcome to our chat system customer");
								connectionQueue.take().start();	
								check = true;
								break;
							}
							
							else{
								toClient.println("false");
							}
						}
					
					}//end if for Customer role 
					
					/*
					 * check if the role is agent is role agent will start agent thread 
					 */
					else if(clientob.getRole().equals("Agent")){
						toClient.println("true");
						//System.out.println("Agent else if runing");
				        toClient.println(clientob.getClientID()+";"+"Welcome to our chat system agent");
						Agent agentob = new Agent(clientob);
						agentob.setAvaliable(true);
						agentob.aThread = new AgentThread(clientSocket,agentob);					
						agentobs.add(agentob);
						agentob.aThread.start();
					}	
	         }
	      }finally{
	    	  clientSocket.close();  
	      }
	      }catch(Exception ex){
	    	  System.out.println("Exception from server"+ ex);
	      }
	    
	  }
	     
	  public boolean checkAgentAvaliable(Customer customerob){
		boolean check = false;
		  //check all agents to see if there any space for this customer 
			for(int i = 0; i< agentobs.size(); i++){
				if(agentobs.get(i).isAvaliable()){
					if(agentobs.get(i).c1Ob == null)
						{
							agentobs.get(i).c1Ob = customerob;
							System.out.println("agent "+ agentobs.get(i).getUsername()+ "customer 1 :"+
									agentobs.get(i).c1Ob.getUsername());
						}
					else
						{
							agentobs.get(i).c2Ob = customerob;		
							System.out.println("agent "+ agentobs.get(i).getUsername()+ "customer 1 :"+
									agentobs.get(i).c2Ob.getUsername());
						}
					customerob.agentOb = agentobs.get(i);	
					System.out.println("customer "+customerob.getUsername()+ " get agent "+ agentobs.get(i).getUsername());
					
					check = true;	
				    break;
				}//end if
			}//end for loop
			
			return check;
	  }
	

}








