package server;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import client.ClientConnection;
import user.User;

public class UserListHolder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 610127901103714213L;
	private LinkedList<ClientConnection> users = new LinkedList<>();
	
	public void setList(LinkedList<ClientConnection>users) {
		this.users = users;
	}
	
	public User getUser(int index) {
		return users.get(index).getUser();
	}
	public ObjectOutputStream getUserOOS(int index) {
		return users.get(index).getOos();
	}
	
	public LinkedList<ClientConnection> getUserList() {
		return users;
	}
	
}
