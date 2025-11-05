package edu.seg2105.edu.server.ui;

import java.util.Scanner;

import edu.seg2105.client.common.ChatIF;
import edu.seg2105.edu.server.backend.EchoServer;

public class ServerConsole implements ChatIF {
	
	// Class Variables ************************************************************************
	
	/**
	 * Default Port Number 
	 */
	final public static int DEFAULT_PORT = 5555;
	
	// Instance Variables ************************************************************************
	
	/**
	 * Instance of server belonging in ServerConsole
	 */
	EchoServer server;
	
	/**
	 * Scanner to read from console
	 */
	Scanner fromConsole;
	
	// Constructor ******************************************************************************
	
	public ServerConsole (int port) {
		server = new EchoServer(port, this);
		
		fromConsole = new Scanner(System.in); 
	}
	
	// Instance Methods *****************************************************************************
	
	  /**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the server's message handler.
	   */
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        server.handleMessageFromServerUI(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    	System.out.println(ex);
	    }
	  }
	
	@Override
	public void display(String message) {
		System.out.println("SERVER MSG> " + message);
	}
	
	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}
		
		ServerConsole chat = new ServerConsole (port);
		chat.accept();
	}
	


}
