package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import client.ClientConnection;
import message.Message;
import user.User;

public class Server
{
	private ServerController controller;
	private ServerSocket serverSocket;
	private Socket socket;
	
	private UserWriter userWriter;


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
					//					controller.remove(socket); // Ersättas med user
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
		private Message message;
		private ObjectOutputStream oos;

		public WriteMessage(Message message, ObjectOutputStream oos)
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