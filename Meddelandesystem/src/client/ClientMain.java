package client;

import GUI.MessageWindow;
import sethTestGUIs.SignUpScreen;

public class ClientMain
{
	public static void main(String[] args)
	{
		ClientController controller = new ClientController();
		Client client = new Client("localhost", 2020, controller);

		new SignUpScreen(controller);

	}
}
