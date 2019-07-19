package client;

import java.io.Serializable;

import user.User;

/**
 * Objekt för att hantera inloggning
 * @author sethoberg
 *
 */

public class LogInObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 925264946365834307L;
	private String name;
	private String password;
	
	public LogInObject(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
}
