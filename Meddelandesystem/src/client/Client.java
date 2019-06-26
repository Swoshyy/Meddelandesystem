package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;

public class Client
{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Client()
	{
		try
		{
			socket = new Socket("localhost", 1999);
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			Message message = new Message("Dino");
			
			oos.writeObject(message);
			oos.flush();
			
		} catch(IOException e)
		{
			e.printStackTrace();
		} finally {
			if(socket != null)
			{
				try
				{
					socket.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Client();
	}
	
}
