package client;

import java.util.ArrayList;

import GUI.MessageWindow;
import displayUsersTest.AllUsersGUI;
import message.Message;
import user.User;

public class ClientController
{
	private Client client;
	private User user;
	private MessageWindow msgWindow;
	private AllUsersGUI listGUI;
	
	
	public void setUserListGUI(AllUsersGUI gui)
	{
		this.listGUI = gui;
	}
	
	public void newClient(Client inClient)
	{
		this.client = inClient; 
	}
	
	public void updateUserList(ArrayList<User> users)
	{
		for(int i=0; i<users.size(); i++)
		{
//			listGUI.addUser(users.get(i));
			msgWindow.displayUser(users.get(i));
		}
	}
	
	public void newUser(User user)
	{
		this.user = user;
		client.connect();
		client.writeUsers(user); //Råkade ta bort denna metoden
	}
	
	public void displayMessage(Message message)
	{
		msgWindow.append(message);
	}
	
	public void sendMessage(Object message)
	{
		client.sendObject(message);
	}
	
	public void setMessageGUI(MessageWindow gui)
	{
		this.msgWindow = gui;
	}
	
}
