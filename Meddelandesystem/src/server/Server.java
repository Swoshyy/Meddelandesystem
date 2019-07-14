package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import client.ClientConnection;
import client.LogInObject;
import message.Message;
import saveUsers.SavedUsers;
import user.User;

public class Server
{
	private ServerController controller;
	private ServerSocket serverSocket;
	private Socket socket;
	private SavedUsers savedUsers = new SavedUsers("files/SavedUsers.dat");
	private LinkedList<ActiveClient> connectedClients = new LinkedList<ActiveClient>();
	

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
	}
	
	
	/**
	 * När en användare lyckats logga in måste listan med användare sckickas till clienten 
	 * @return
	 */
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
//			Socket socket = null;
			try
			{
				while (true)
				{
					socket = serverSocket.accept();
					System.out.println("new socket");
					new ClientHandler(socket);
					System.out.println("new created");
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

				Object recievedObject;
				while (true)
				{

					recievedObject = (Object) ois.readObject();
					
					if(recievedObject instanceof Message) {
						Message message = (Message) recievedObject;
						controller.newMessage(message);
					}
					
					
					/*
					 * Logga in användare med loginobject
					 */
					if(recievedObject instanceof LogInObject) {
						LogInObject logInUser = (LogInObject) recievedObject;
						savedUsers.controlUser(logInUser.getUser());
						LoginStatus status = new LoginStatus();
						
						if(savedUsers.logInUser(logInUser.getUser().getName(), logInUser.getUser().getPassword()) == 1) {
							//Här ska ett User objekt tilldelas clientHandlern???
							//Eller ett activeclient objekt ska skapas
							System.out.println("inloggning lyckad");
							status.setLoginSucessfull(1);
							status.setUser(logInUser.getUser());
							connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
							new WriteMessage(status, oos);
							new WriteMessage(connectedClients, oos);
						}
						else {
							System.out.println("inloggning ej godkänd");
						}
						
						
					}
					
					
					/*
					 * Skapa ny användare med User objekt
					 */
					if(recievedObject instanceof User) {
						User user = (User) recievedObject;
						savedUsers.controlUser(user);
						LoginStatus status = new LoginStatus();
						
						if(savedUsers.controlUser(user) == 0) {
							//Här ska ett User objekt tilldelas clientHandlern???
							//Eller ett activeclient objekt ska skapas
							System.out.println("Ny användare skapad!");
							status.setLoginSucessfull(1);
							status.setUser(user);
							connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
							new WriteMessage(status, oos);
							new WriteMessage(connectedClients, oos);
						}
						else {
							System.out.println("Användare finns redan");
						}
						
					}
					
					/*
					 * We could call controller first to send to all but thats a story for another
					 * day, which is now
					 */

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
		private Object object;
		private ObjectOutputStream oos;

		public WriteMessage(Object object, ObjectOutputStream oos)
		{
			this.object = object;
			this.oos = oos;
			start();
		}

		public void run()
		{
			try
			{
				oos.writeObject(object);
				oos.flush();
			} catch (IOException e)
			{
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	
	
}
