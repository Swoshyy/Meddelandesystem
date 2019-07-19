package server;

import java.io.ObjectOutputStream;
import java.io.Serializable;

import user.User;

public class ActiveClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3525747833055494421L;
	private ObjectOutputStream oos;
	private User user;

	
	public ActiveClient(ObjectOutputStream oos, User user) {
		this.oos = oos;
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public ObjectOutputStream getOuputStream() {
		return this.oos;
	}
	
	
}
