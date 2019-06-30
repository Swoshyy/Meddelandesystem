package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import GUI.MessageWindow;
import message.Message;
import sethTest.TestSebbe;

public class Client
{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private MessageWindow msgWindow;
	private TestSebbe ts = new TestSebbe();

	public Client()
	{
		try
		{
			socket = new Socket("localhost", 2013);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			msgWindow = new MessageWindow(this);
			new Read().start();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
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
			try
			{
				while (true)
				{
					message = (Message) ois.readObject();
					msgWindow.append(message.getText() + "");
					
					if(message.getImage() != null)
					{
//						msgWindow.showImage(message.getImage());
						ts.drawImage(message.getImage());
//						JOptionPane.showConfirmDialog(null, message.getImage());

					}
					
				}
			} catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			disconnectFromServer();
		}

		private void disconnectFromServer()
		{
			try
			{
				ois.close();
				oos.flush();
				oos.close();
				socket.close();
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}