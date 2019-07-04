package server;

public class ServerMain
{
	public static void main(String[] args)
	{
		ServerController controller = new ServerController();
		new Server(2020, controller);
	} 
	
}
