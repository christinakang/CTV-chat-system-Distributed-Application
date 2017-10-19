package ChatAppServerSide;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Object.*;

public class CustomerThread extends Thread
{
	Customer customer;
    BufferedReader input;
    PrintWriter output;
    public CustomerThread(Socket client,Customer customer)throws Exception
    {
        this.customer= customer;
    	input=  new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(),true);

      }
    
    public void sendMessage(String chatuser,String chatmsg)
    {
        output.println(chatuser+";"+ chatmsg);
    }
    
    public void run()
    {
        String line;
        
        try
        {
            while(true)
            {
            	System.out.println("customer thread running");
                line = input.readLine();
                customer.setLine(line);
                //need to get end key words to remove the object 
                if(line.equals("end"))
                {
                	System.out.println("End from customer");
                	MainServer.customerobs.remove(customer);
                    break;
                }
                sendtoAgent(customer);
            }
        }
        catch(Exception ex){
            System.out.print(ex);
        }
    }
    
    public static void sendtoAgent(Customer customerob){
    	customerob.agentOb.aThread.sendMessage(customerob.getUserID(), customerob.getLine());
    	
    }
    
    
}
