package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import message.Message;

public class Server
{
	private ServerController controller;

	public Server(int inPort, ServerController inController)
	{
		this.controller = inController;
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
			Socket socket = null;
			try
			{
				ServerSocket serverSocket = new ServerSocket(port);
				socket = serverSocket.accept();

				new ClientHandler(socket);

			} catch(IOException e)
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

	private class ClientHandler extends Thread
	{
		private Socket socket;
		ObjectInputStream ois;
		ObjectOutputStream oos;

		public ClientHandler(Socket inSocket)
		{
			this.socket = inSocket;
			start();
		}

		public void run()
		{
			try
			{
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());

				Message message;
				while(true)
				{
					message = (Message) ois.readObject();

					/* We could call controller first to send to all
					 * but thats a story for another day, which is now */
					oos.writeObject(message);
					oos.flush();
				}

			} catch(IOException | ClassNotFoundException e)
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
