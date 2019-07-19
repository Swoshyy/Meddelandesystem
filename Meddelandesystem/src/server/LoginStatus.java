package server;

import java.io.Serializable;

import user.User;

/**
 * If log in is sucessfull the int is set to 1 and if log in is not accepted 
 * the int is set to 0
 * This class could be changed to return a boolean instead
 * @author sethoberg
 *
 */
public class LoginStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8253771472258805195L;
	private int loginSuccessfull = 0;
	private User logInUser;
	
	
	public LoginStatus() {
		loginSuccessfull = 0;
	}
	
	/**
	 * 1 for successfull log in
	 * 0 for unsuccessfull log in
	 * @param status
	 */
	public void setLoginSucessfull(int status) {
		loginSuccessfull = status;
	}
	
	/**
	 * If user is allowed to log in 1 is returned, if not 0 is returned
	 * @return
	 */
	public int getLoginStatus() {
		return loginSuccessfull;
	}
	
	public void setUser(User user) {
		this.logInUser = user;
	}
	
	public User getLoggedInUser() {
		return logInUser;
	}

}
