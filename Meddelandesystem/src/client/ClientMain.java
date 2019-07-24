package client;

import GUI.MessageWindow;
import sethTestGUIs.LoginScreen;
import sethTestGUIs.SignUpScreen;

public class ClientMain
{
	public static void main(String[] args)
	{
		ClientController controller = new ClientController();
//		MessageWindow msgWindow = new MessageWindow(controller);
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
