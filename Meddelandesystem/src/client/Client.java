package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import message.Message;
import message.MessageQueue;
import user.User;

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
	private UserWriter userWriter;

	public Client(String inAddress, int inPort, ClientController inController)
	{
		this.address = inAddress;
		this.port = inPort;
		this.controller = inController;

		controller.newClient(this);

	}

	public void connect()
	{
		try
		{
			socket = new Socket(address, port);
			oos = new ObjectOutputStream(socket.getOutputStream());

			msgWriter = new MessageWriter();
			msgReader = new MessageReader();
			userWriter = new UserWriter();
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
				while (true)
				{
					Object obj = ois.readObject();
					if (obj instanceof Message)
					{
						Message message = (Message) obj;
						controller.displayMessage(message);
					} else if (obj instanceof ArrayList<?>)
					{
						ArrayList<User> users = (ArrayList<User>) obj;
						controller.updateUserList(users);
					}
				}
			} catch (IOException | ClassNotFoundException e)
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
	
	public void writeUsers(User user)
	{
		userWriter.newUsers(user);
	}
	
	private class UserWriter
	{
		private User newUser;
		public UserWriter()
		{
		}

		public void newUsers(User user)
		{
			this.newUser = user;
			write();
		}

		public void write()
		{
			try
			{
				oos.writeObject(newUser);
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

}
