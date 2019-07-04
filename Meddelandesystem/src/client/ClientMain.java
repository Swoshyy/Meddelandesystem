package client;

import GUI.MessageWindow;

public class ClientMain
{
	public static void main(String[] args)
	{
		ClientController controller = new ClientController();
		MessageWindow msgWindow = new MessageWindow(controller);
		controller.setGUI(msgWindow);
	
		new Client("localhost", 2020, controller);

	}
}
