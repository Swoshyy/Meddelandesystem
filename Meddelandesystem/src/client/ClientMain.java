package client;

import GUI.MessageWindow;
import sethTestGUIs.LoginScreen;
import sethTestGUIs.SignUpScreen;

public class ClientMain
{
	public static void main(String[] args)
	{
		ClientController controller = new ClientController();
		Client client = new Client("localhost", 2020, controller);

		LoginScreen hej = new LoginScreen(controller);
//		new SignUpScreen(controller);

	}
}
