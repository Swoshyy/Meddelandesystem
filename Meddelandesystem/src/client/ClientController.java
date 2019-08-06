package client;

import java.util.LinkedList;

import GUI.MessageWindow;
import message.Message;
import sethTestGUIs.LoginScreen;
import sethTestGUIs.SignUpScreen;
import user.User;

public class ClientController
 {
	private Client client;
	private MessageWindow msgWindow;
	private LoginScreen logInScreen;
	private SignUpScreen signupScreen;
	
	
	public void newClient(Client inClient)
	{
		this.client = inClient; 
		logInScreen = new LoginScreen(this);
	}
	
	public void newLoginScreen(LoginScreen logInScreen) {
		this.logInScreen = logInScreen;
	}
	
	public void addListOfUsers(LinkedList<User> users) {
		msgWindow.addListOfUsers(users);
	}
	
	
	public void displayMessage(Message message)
	{
		msgWindow.append(message);
	}
	
	public void sendMessage(Object message)
	{
		client.sendObject(message);
	}
	
	public void setGUI(MessageWindow gui)
	{
		this.msgWindow = gui;
	}
	
	public void setLoginGUI(LoginScreen loginGUI) {
		this.logInScreen = loginGUI;
	}
	
	public void closeLogin() {
		logInScreen.closeLogin();
	}
	
	public void setSignupGUI(SignUpScreen signupGUI) {
		this.signupScreen = signupGUI;
	}
	
	public void closeSignUp() {
		signupScreen.closeSignUp();
	}
	

	public User getNewUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
