package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import edu.seg2105.edu.server.ui.ServerConsole;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
//  
//  /**
//   * The default port to listen on.
//   */
//  final public static int DEFAULT_PORT = 5555;
  
	ServerConsole serverUI;
	
  //Constructors ****************************************************
   
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ServerConsole serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
    
    try {
		listen(); // Start listening for connections
	} catch (Exception ex) {
		System.out.println("ERROR - Could not listen for clients!");
	}
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	if (msg.toString().equals("#quit") || msg.toString().equals("#logoff")) {
		clientDisconnected(client);
	}
	else {
	    System.out.println("Message received: " + msg + " from " + client);
	    this.sendToAllClients(msg);
	}
  }
  
  /**
   * This method handles any messages received from the server console.
   * 
   * @param msg The message received from the console
   * @throws IOException 
   */
  public void handleMessageFromServerUI (String msg) throws IOException {
	  if (msg.startsWith("#")) {
		  handleCommand(msg);
	  }
	  else {
		  this.sendToAllClients("SERVER MSG> " + msg);
		  serverUI.display(msg);
	  }
  }
  
  /**
   * This method handles any commands received from the server console
   */
  public void handleCommand (String command) throws IOException{
	  if (command.equals("#quit")) {
		  	serverUI.display("Server Shutting Down");
		  	quit();
	  }
	  else if (command.equals("#stop")) {
		    stopListening();
	  }
	  else if (command.equals("#close")) {
			close();
			serverUI.display("Server Closed");
	  }
	  else if (command.startsWith("#setport")) {
		    if (isListening()) {
		    	serverUI.display("Can not change port while server is on.");
		    }
		    else {
		    	try {
		    	String [] temp = command.split(" ");
		    	setPort(Integer.parseInt(temp[1]));
		    	serverUI.display("Port Change Successful");
		    	}
		    	catch (Exception e) {
		    		serverUI.display("Error: Invalid Port Input");
		    	}		    
		    }
	  }
	  else if (command.equals("#start")) {
		  	if (isListening()) {
		  		serverUI.display("Server is already running.");
		  	}
		  	else 
		  		listen();
	  } 
	  else if (command.equals("#getport")) {
		  	serverUI.display(getPort()+"");
	  }
	  else {
		  serverUI.display("Invalid Command");
	  }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("Client has connected");
	}

	synchronized protected void clientDisconnected(ConnectionToClient client) {
		super.clientDisconnected(client);
		System.out.println("Client Has Disconnected");
	}
	
	public void quit() throws IOException{
		close();
		System.exit(0);
	}
}
//End of EchoServer class
