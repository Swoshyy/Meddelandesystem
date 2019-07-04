package server;

import java.util.LinkedList;

import client.Client;
import client.ClientConnection;
import message.Message;

public class ServerController
{
	private Server server;
	private LinkedList<ClientConnection> clients = new LinkedList<>();
	private int[] pos = new int[2];
	private static int i=0;

	public void registerServer(Server inServer)
	{
		this.server = inServer; 
	}

	public void newClient(ClientConnection cc)
	{
		System.out.println("hjjjdsadas added");
		clients.add(cc);
		pos[i] = i;
		i++;
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
