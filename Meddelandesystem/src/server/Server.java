package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import client.ClientConnection;
import message.Message;

public class Server
{
	private ServerController controller;
	private ServerSocket serverSocket;
	private Socket socket;


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
				while (true)
				{

					message = (Message) ois.readObject();
					controller.newMessage(message);
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

}
