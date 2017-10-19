package ChatAppServerSide;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Object.*;

public class AgentThread extends Thread
{
    
	Agent agent;
    BufferedReader input;
    PrintWriter output;
    private static double simi = 0.5;
    public AgentThread(Socket client,Agent agent)throws Exception
    {
    	this.agent = agent;
        input=  new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(),true);
    }
    
    public void sendMessage(String chatuser,String chatmsg)
    {
        output.println(chatuser+";"+chatmsg);
    }
    
    
    public void run()
    {
        String line;
        
        try
        {
            while(true)
            {
                line = input.readLine();
                if(line.equals("end"))
                {
                    System.out.println("End from agent");
                	MainServer.agentobs.remove(agent);
                    break;
                }
                
                agent.setLine(line);      
                sendtoCustomer(agent);
            }
        }
        catch(Exception ex){
            System.out.print(ex+ "from agent thread");
        }
    }
    
    
	public static void sendtoCustomer(Agent agentOb){
		//find the customer 
		
		//if the 2 customer ask same thing 
	    if(agentOb.getLine().startsWith("1")){	
	    	agentOb.setLine(agentOb.getLine().substring(1));
	    	if(checkLine(agentOb)){
	    		sendtoAll(agentOb);
	    	}
	    	else{
	    		agentOb.c1Ob.cThread.sendMessage(agentOb.getUserID(),agentOb.getLine());
	    	}
		}
		
		else if(agentOb.getLine().startsWith("2"))
		{
			agentOb.setLine(agentOb.getLine().substring(1));
			if(checkLine(agentOb)){
	    		sendtoAll(agentOb);
	    	}   
			else{
				agentOb.c2Ob.cThread.sendMessage(agentOb.getUserID(),agentOb.getLine());
			}
		}
		else if(agentOb.getLine().startsWith("3")){
			agentOb.setLine(agentOb.getLine().substring(1));
			sendtoAll(agentOb);
		}
		else
		{
			agentOb.aThread.sendMessage(agentOb.getUserID(),"Can't find customer");
		}
		
}
	
	public static void sendtoAll(Agent agentOb){
		
		agentOb.c1Ob.cThread.sendMessage(agentOb.getUserID(),agentOb.getLine());
		agentOb.c2Ob.cThread.sendMessage(agentOb.getUserID(),agentOb.getLine());
	}
	
	public static boolean checkLine(Agent agentOb){
		boolean check = false;
		
		if(agentOb.c1Ob == null || agentOb.c2Ob == null)
		{
			check = false;
		}	
		else if(agentOb.c1Ob.getLine()==null || agentOb.c2Ob.getLine()==null){
			check = false;
		}
		else if(checkSimilarity(agentOb.c1Ob.getLine(),agentOb.c2Ob.getLine()) == true){
			check = true;
		}
		return check;
	}
	
	public static boolean checkSimilarity(String line1, String line2){
		boolean check = true;
		double count = 0;
		line1 = line1.toLowerCase();
		line2 = line2.toLowerCase();
		ArrayList<String> arr1 = new ArrayList<String>();
		ArrayList<String> arr2 = new ArrayList<String>();
		Scanner linecheck1 = new Scanner(line1);
        Scanner linecheck2 = new Scanner (line2);
		while(linecheck1.hasNext()){
			arr1.add(linecheck1.next());
		}
		while(linecheck2.hasNext()){
			arr2.add(linecheck2.next());
		}
		
		linecheck1.close();
		linecheck2.close();
		
		for(String s1: arr1){
			for(String s2: arr2){
				if(s1.equals(s2)){
					count++;
				}
			}
		}
		System.out.println("count: "+count);
		
		double size1 = arr1.size();
		double size2 = arr2.size();
		double size;
		
		if(size1>size2)
			size = size1;
		else 
			size = size2;
		System.out.println("size "+size);
		double similarity;
		similarity = (double)count/size;
		if(similarity <= simi)
			{
			    check = false;
			}
        System.out.println(similarity);
		return check;
	}
}
