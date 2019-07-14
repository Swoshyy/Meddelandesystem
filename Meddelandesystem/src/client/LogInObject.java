package client;

import user.User;

public class LogInObject {
	private User user;
	
	public LogInObject(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
	
}
