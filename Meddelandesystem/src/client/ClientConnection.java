package client;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import user.User;

public class ClientConnection 
{
	/**
	 * 
	 */
//	private static final long serialVersionUID = 5990443356487975566L;
	private ObjectOutputStream oos;
	private Socket socket;
	private User user;

	public ClientConnection(ObjectOutputStream oos, Socket socket, User user)
	{
		this.oos = oos; 
		this.socket = socket;
		this.user = user;
	}

	public ObjectOutputStream getOos()
	{
		return this.oos;
	}

	public Socket getSocket()
	{
		return this.socket;
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
}
