package client;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection
{
	private ObjectOutputStream oos;
	private Socket socket;

	public ClientConnection(ObjectOutputStream oos, Socket socket)
	{
		this.oos = oos; 
		this.socket = socket;
	}

	public ObjectOutputStream getOos()
	{
		return this.oos;
	}

	public Socket getSocket()
	{
		return this.socket;
	}
}
