package client;

import GUI.MessageWindow;
import message.Message;

public class ClientController
{
	private Client client;
	private MessageWindow msgWindow;
	
	public void newClient(Client inClient)
	{
		this.client = inClient;
	}
	
	public void displayMessage(Message message)
	{
		msgWindow.append(message.getText() + "");
	}
	
	public void sendMessage(Message message)
	{
		client.sendMessage(message);
	}
	
	public void setGUI(MessageWindow gui)
	{
		this.msgWindow = gui;
	}
	
	
}
