package saveUsers;

import java.io.Serializable;

/**
 * 
 * @author sethoberg
 *
 */

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3061787204562448072L;
	private String name; 
	private String password;
	
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	
	public int checkPassword(String name, String password) {
		
		if (this.name.equals(name) && this.password.equals(password)) {
			return 1;
		}
		else {
			return 0;
		}
		
	}
	

}
