package client;

import sethTestGUIs.LoginScreen;
//import sethTestGUIs.LoginScreen;
import sethTestGUIs.SignUpScreen;

public class ClientMain
{
	public static void main(String[] args)
	{
		
//		MessageWindow msgWindow = new MessageWindow(controller);
//		controller.setGUI(msgWindow);
		ClientController controller = new ClientController();
		new Client("localhost", 2020, controller);
		
//		SignUpScreen sign = new SignUpScreen(controller);
//		LoginScreen logInScreen = new LoginScreen(controller);
//		controller.setLoginGUI(logInScreen);
//		controller.setSignupGUI(sign);
		
//		try
//		{
//			new Client(InetAddress.getByName("LAPTOP-807OMURG"), 2020, controller);
//		} catch (UnknownHostException e)
//		{
//			e.printStackTrace();
//		}


	}
}
