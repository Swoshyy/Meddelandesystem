package server;

import java.awt.image.ImagingOpException;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import message.Message;

public class Server
{
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private Date date = new Date();

	public Server()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(2010);
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

					oos.writeObject(message);
					oos.flush();

					if (message.getImage() != null)
					{
						/* Append image to text area */
					}
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

	public static void main(String[] args)
	{
		new Server();
	}

}
