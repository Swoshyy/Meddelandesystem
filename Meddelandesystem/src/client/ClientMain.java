package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import GUI.MessageWindow;

public class ClientMain
{
	public static void main(String[] args)
	{
		ClientController controller = new ClientController();
		MessageWindow msgWindow = new MessageWindow(controller);
		controller.setGUI(msgWindow);
	
		new Client("localhost", 2020, controller);
//		try
//		{
//			new Client(InetAddress.getByName("LAPTOP-807OMURG"), 2020, controller);
//		} catch (UnknownHostException e)
//		{
//			e.printStackTrace();
//		}

	}
}
