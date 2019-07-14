package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import client.ClientConnection;
import message.Message;
import user.User;

public class ServerController
{
	private Server server;
	private LinkedList<ClientConnection> clients = new LinkedList<>();
	private ArrayList<User> connectedUsers = new ArrayList<>();

	public void registerServer(Server inServer)
	{
		this.server = inServer; 
	}

	public void newClient(ClientConnection cc)
	{
		clients.add(cc);
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

	public void addNewUser(User user)
	{
		connectedUsers.add(user);
		writeUsers();
	}
	
	public void writeUsers()
	{
		server.writeUsers(connectedUsers);
	}

}
