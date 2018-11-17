package edu.gvsu.cis.cis656.message;

import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import edu.gvsu.cis.cis656.clock.VectorClock;
import edu.gvsu.cis.cis656.queue.PriorityQueue;

public class Client {
	
	private String userName;
	private static int myPid;
	VectorClock myClock = new VectorClock();
	MessageRecv messageRecv;
	DatagramSocket socket = null;
	InetAddress address = null;
	int port = 8000;
	
	
	public Client(String userName) {
		this.userName = userName;
		Message message = new Message(0, this.userName, 0, null, this.userName);
		Message.sendMessage(message, socket, address, port);
		Message ackMessage = Message.receiveMessage(socket);
		if (ackMessage.type == 1) {
			myPid = ackMessage.pid;
		}
		else if (ackMessage.type == 3) {
			System.out.println("Username already taken.");
			System.exit(0);
		}
		
		this.messageRecv = new MessageRecv();
        Thread t = new Thread(this.messageRecv);
        t.start();
	}
	
	
	public static int sumClockValues(VectorClock msg) {
		int sum = 0;
		for(String key: msg.clock.keySet()) {
    		sum += msg.clock.get(key);
		}	
		return 0;
	}
	
	class MessageRecv implements Runnable
    {
       
        public void run()
        {
        	MessageComparator mc =new MessageComparator();
        	PriorityQueue<Message> pq = new PriorityQueue<Message>(mc);
        	while(true) {
        		Message msg = Message.receiveMessage(socket);
        		pq.add(msg);
        		Message topMsg = pq.peek();	
        		while (topMsg != null) {
        		   	//if topMsg meets the print condition (see above description!) 
        			if (sumClockValues(topMsg.ts) == sumClockValues(myClock)) {
        			 //print topMsg 
        			   System.out.println(topMsg.sender+": "+topMsg.message);
        			   pq.poll();
        			   myClock.update(topMsg.ts);
        		   }
        		   topMsg = pq.peek();
        		}
        	}
        }
        public void stop()
        {
           
        }
    }
	
	public void runCmdShell() throws IOException {
		while(true) {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String message = in.readLine();
			this.myClock.tick(myPid);
			Message myMessage = new Message(2, this.userName, this.myPid, this.myClock, message);
			Message.sendMessage(myMessage, socket, address, port);
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Enter username:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String userName = in.readLine();
		Client client = new Client(userName);
		client.runCmdShell();
	}

}
