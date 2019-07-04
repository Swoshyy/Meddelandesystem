package server;

import java.util.LinkedList;

import client.Client;
import client.ClientConnection;
import message.Message;

public class ServerController
{
	private Server server;
	private LinkedList<ClientConnection> clients = new LinkedList<>();
	
	public void registerServer(Server inServer)
	{
		this.server = inServer;
	}
	
	public void newClient(ClientConnection cc)
	{
		System.out.println("hjjjdsadas added");
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
			server.sendMessage(message, clients.get(i).getOos());
		}
	}
	
}
