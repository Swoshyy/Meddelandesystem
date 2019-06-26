package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import GUI.MessageWindow;
import message.Message;

public class Client
{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private MessageWindow msgWindow;

	public Client()
	{
		try
		{
			socket = new Socket("localhost", 2010);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			msgWindow = new MessageWindow(this);
			
			new Read().start();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
//		finally
//		{
//			if (socket != null)
//			{
//				try
//				{
//					socket.close();
//				} catch (IOException e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}
	}

	public void sendMessage(Message message)
	{
		try
		{
			oos.writeObject(message);
			oos.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new Client();
	}
	
	private class Read extends Thread
	{
		public void run()
		{
			Message message = null;
			while(true)
			{
				try
				{
					message = (Message) ois.readObject();
					msgWindow.append(message.getText() + "");
					
				} catch(IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
