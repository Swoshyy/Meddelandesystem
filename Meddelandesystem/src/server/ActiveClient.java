package server;

import java.io.ObjectOutputStream;

import user.User;

public class ActiveClient {
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
