package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import client.ClientConnection;
import client.LogInObject;
import message.Message;
import saveUsers.SavedUsers;
//import server.Server.WriteMessage;
import user.User;

public class Server
{
	private ServerController controller;
	private ServerSocket serverSocket;
	private Socket socket;
	private UserWriter userWriter;
	private SavedUsers savedUsers = new SavedUsers("files/SavedUsers.dat");
	private LinkedList<ActiveClient> connectedClients = new LinkedList<ActiveClient>();
	private ServerLogger logger;
	
	public Server(int inPort, ServerController inController)
	{
		this.controller = inController; 
		try
		{
			serverSocket = new ServerSocket(inPort);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		controller.registerServer(this);
		new ClientListener(inPort);
		logger = new ServerLogger();
		logger.log("server up and running");
	}

	public LinkedList<ActiveClient> getConnectedClients() {
		return this.connectedClients;
	}
	
	private class ClientListener extends Thread
	{
		private int port;

		public ClientListener(int inPort)
		{
			this.port = inPort;
			start();
		}

		public void run()
		{
			try
			{
				while (true)
				{
					socket = serverSocket.accept();
					new ClientHandler(socket);
				}
			} catch (IOException e)
			{
				try
				{
					socket.close();
					//					controller.remove(socket); // Ers�ttas med user
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}

	private class ClientHandler extends Thread
	{
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public ClientHandler(Socket inSocket)
		{
			this.socket = inSocket;
			try
			{
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				controller.newClient(new ClientConnection(oos, socket));
				userWriter = new UserWriter(oos);

			} catch (IOException e)
			{
				e.printStackTrace();
			}

			start();
		}

		public void run()
		{
			try
			{
				Message message;
				Object obj;
				while (true)
				{
					//					message = (Message) ois.readObject();
					obj = ois.readObject();
					if(obj instanceof Message)
					{
						message = (Message) obj;
						controller.newMessage(message);
					} else if(obj instanceof User)
					{
						controller.addNewUser((User) obj);
						
						// skriv user?
					}
					
					else if(obj == null) {
						System.out.println("objekt är null");
					}
					
					
					/*
					 * Logga in användare med loginobject
					 */
					else if(obj instanceof LogInObject) {
						LogInObject logInUser = (LogInObject) obj;
						LoginStatus status = new LoginStatus();
						
//						User haj = logInUser.getUser();
						
						if(savedUsers.logInUser(logInUser.getName(), logInUser.getPassword()) == 1) {
							System.out.println("inloggning lyckad");
							status.setLoginSucessfull(1);
							status.setUser(savedUsers.getUser());
							connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
							//Alla klienters listor behöver uppdateras här
							new WriteMessage(status, oos); //För att skicka statusen av inloggningen till clienten (lyckad/ej lyckad inloggning)
							new WriteMessage(connectedClients, oos); //För att uppdatera lista med användare
						}
						else {
							System.out.println("inloggning ej godkänd");
						}
						
						
					}
					
					
					
					
					
					/*
					 * Skapa ny användare med User objekt
					 */
					else if(obj instanceof User) {
						User user = (User) obj;
						LoginStatus status = new LoginStatus();
						
						if(savedUsers.controlUser(user) == 0) {
							System.out.println("Ny användare skapad!");
							savedUsers.saveNewUser(user);
							status.setLoginSucessfull(1);
							status.setUser(user);
							connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
							new WriteMessage(status, oos);
//							new WriteMessage(connectedClients, oos);
						}
						else {
							System.out.println("Användare finns redan");
						}
						
					}
					
					
					else {
						System.out.println("Objekt kan inte typkonverteras");
					}
					
				}
			} catch (IOException | ClassNotFoundException e)
			{
				try
				{
					if(socket != null)
					{
						socket.close();
					}
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}

	public void sendMessage(Message inMessage, ObjectOutputStream inOos)
	{
		new WriteMessage(inMessage, inOos);

	}

	private class WriteMessage extends Thread
	{
		private Object message;
		private ObjectOutputStream oos;

		public WriteMessage(Object message, ObjectOutputStream oos)
		{
			this.message = message;
			this.oos = oos;
			start();
		}

		public void run()
		{
			try
			{
				oos.writeObject(message);
				oos.flush();
			} catch (IOException e)
			{
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void writeUsers(ArrayList<User> users)
	{
		userWriter.newUsers(users);
	}
	
	private class UserWriter
	{
		private ArrayList<User> users;
		private ObjectOutputStream userOos;

		public UserWriter(ObjectOutputStream oos)
		{
			this.userOos = oos;
		}

		public void newUsers(ArrayList<User> users)
		{
			this.users = users;
			write();
		}

		public void write()
		{
			try
			{
				userOos.writeObject(users);
				userOos.flush();
			} catch (IOException e)
			{
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
}