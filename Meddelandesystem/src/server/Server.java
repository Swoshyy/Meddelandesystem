package server;

import java.awt.Image;
import java.awt.image.ImagingOpException;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import client.Client;
import message.Message;
import user.User;

public class Server
{
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private Date date = new Date();
	private LinkedList<User> userList = new LinkedList<>();
	private User user;
	
//	public Server(User user)
	public Server()
	{
		this.user = user;
		try
		{
			ServerSocket serverSocket = new ServerSocket(2013);
			Socket socket = serverSocket.accept();

			Runnable task = new ClientHandler(socket);
			threadPool.submit(task);

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private class ClientHandler implements Runnable
	{
		private Socket clientSocket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private Boolean ShouldBeRunning;

		public ClientHandler(Socket inSocket)
		{
			this.clientSocket = inSocket;
			this.ShouldBeRunning = true;
			try
			{
				ois = new ObjectInputStream(clientSocket.getInputStream());
				oos = new ObjectOutputStream(clientSocket.getOutputStream());
				user.setOis(ois);
				user.setOos(oos);
				userList.add(user);
			} catch (IOException e)
			{
				try
				{
					disconnectClient();

				} catch(ImagingOpException ee)
				{
					ee.printStackTrace();
				}
			}
		}

		public void run()
		{
			Message message = null;

			try
			{
				while (ShouldBeRunning)
				{
					message = (Message) ois.readObject();
					
					if (message.getImage() != null)
					{
						System.out.println("HEJ, jag fick en image");

						
					}
					
					for(int i=0; i<userList.size(); i++) {
						user.getOos().writeObject(message);
						user.getOos().flush();
					}
//					oos.writeObject(message);
//					oos.flush();

					
				}
			} catch (IOException | ClassNotFoundException e)
			{
				try
				{
					ShouldBeRunning = false;
					disconnectClient();

				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}

		private void disconnectClient()
		{
			try
			{
				System.out.println("Kopplar ner klient");
				oos.flush();
				oos.close();
				ois.close();
				threadPool.shutdown();
				clientSocket.close();

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
//	private class ClientAddress {
//		
//		private Socket socket;
//		private ObjectOutputStream oos;
//		private ObjectInputStream ois;
//		
//		public ClientAddress(Socket socket) {
//			try {
//			this.socket = socket;
//			oos = new ObjectOutputStream(socket.getOutputStream());
//			ois = new ObjectInputStream(socket.getInputStream());
//			
//			user.setOos(oos);
//			user.setOis(ois);
//			
//			}catch (IOException e ) {
//				e.printStackTrace();
//			}
//			}
//	}

	public static void main(String[] args)
	{
		new Server();
	}

}
