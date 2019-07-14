package server;

import java.net.Socket;
import java.util.LinkedList;

import client.ClientConnection;
import message.Message;
import saveUsers.SavedUsers;

public class ServerController
{
	private Server server;
	private LinkedList<ClientConnection> clients = new LinkedList<>();
	private SavedUsers savedUsers = new SavedUsers("files/savedUsers.dat");
	

	public void registerServer(Server inServer)
	{
		this.server = inServer; 
	}

	public void newClient(ClientConnection cc)
	{
		System.out.println("hjjjdsadas added");
		clients.add(cc);

	}

	public void remove(Socket socket)
	{
		for(int i=0; i<clients.size(); i++)
		{
			if(clients.get(i).getSocket() == socket)
			{
				System.out.println("In");
				clients.remove(i);
				System.out.println("but not out");
			}
		}
	}

	public LinkedList<ClientConnection> getClientList()
	{
		return clients;
	}

	public void newMessage(Message message)
	{
		for(int i=0; i<clients.size(); i++)
		{	
			if(clients.get(i) != null)
			{
				server.sendMessage(message, clients.get(i).getOos());
			}
		}
	}

}
