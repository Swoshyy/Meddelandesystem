package client;

import GUI.MessageWindow;
import message.Message;
import sethTestGUIs.LoginScreen;
import user.User;

public class ClientController
 {
	private Client client;
	private MessageWindow msgWindow;
	private LoginScreen logInScreen = new LoginScreen(this);
	
	
	public void newClient(Client inClient)
	{
		this.client = inClient; 
	}
	
	public void displayMessage(Message message)
	{
		msgWindow.append(message);
	}
	
	public void sendMessage(Object message)
	{
//		client.sendMessage(message);
		client.sendObject(message);
	}
	
	public void setGUI(MessageWindow gui)
	{
		this.msgWindow = gui;
	}

	public User getNewUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
