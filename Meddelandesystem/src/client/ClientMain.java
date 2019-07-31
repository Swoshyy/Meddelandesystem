package client;

import GUI.MessageWindow;
import sethTestGUIs.LoginScreen;
import sethTestGUIs.SignUpScreen;
import user.User;

public class ClientMain
{
	public static void main(String[] args)
	{
		ClientController controller = new ClientController();
//		MessageWindow msgWindow = new MessageWindow(controller, new User("TestUser"));
//		controller.setGUI(msgWindow);
	 
		new Client("localhost", 2020, controller);
		LoginScreen hej = new LoginScreen(controller);
		controller.setLoginGUI(hej);
//		try
//		{
//			new Client(InetAddress.getByName("LAPTOP-807OMURG"), 2020, controller);
//		} catch (UnknownHostException e)
//		{
//			e.printStackTrace();
//		}


	}
}
