package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import message.Message;
import message.MessageQueue;

public class Client
{
	private Socket socket;
	private String address;
//	private InetAddress address;
	private int port; 
	
	private ObjectOutputStream oos;
	
	private ClientController controller;
	private MessageWriter msgWriter;
	private MessageReader msgReader;
	
	public Client(String inAddress, int inPort, ClientController inController)
//	public Client(InetAddress inAddress, int inPort, ClientController inController)
	{
		this.address = inAddress;
		this.port = inPort;
		this.controller = inController;
		
		controller.newClient(this);
	
		connect();
		
	}
	
	public void connect()
	{
		try
		{
			socket =  new Socket(address, port);
			
			oos = new ObjectOutputStream(socket.getOutputStream());

			
			msgWriter = new MessageWriter();
			msgReader = new MessageReader();
			msgReader.start();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message message)
	{
		msgWriter.newMess(message);
	}
	
	private class MessageWriter
	{
		private MessageQueue<Message> msgQueue;
		private Message message;
		
		public MessageWriter()
		{
		}
		
		public void newMess(Message mess)
		{
			this.message = mess;
			write();
		}
		
		public void write()
		{
			try
			{
//				while(true)
//				{
					oos.writeObject(message);
					oos.flush();
//				}
			} catch (IOException e)
			{
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
//				e.printStackTrace();
			}
		}
		
	}
	
	private class MessageReader extends Thread
	{
		public void run()
		{
			try
			{
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				while(true)
				{
					Message message = (Message) ois.readObject();
					System.out.println("MEssage read from client");
					controller.displayMessage(message);
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
