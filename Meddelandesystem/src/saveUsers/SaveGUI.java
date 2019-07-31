package saveUsers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import user.User;


/**
 * Class used for testing log in and signing up, now replaced by the classes LoginScreen and SignUpScreen
 * @author sethoberg
 *
 */

public class SaveGUI extends JPanel {
	
	private JFrame windowObject = new JFrame("Save user");
	
	private JPanel centerPanel = new JPanel();
	
	private JLabel nameLabel = new JLabel("Name: ");
	private JTextField nameField = new JTextField("Enter username here");
	
	private JLabel passwordLabel = new JLabel("Password: ");
	private JTextField passwordField = new JTextField("Enter password here"); 
	
	
	private JPanel buttonPanel = new JPanel(); 
	
	private JButton newUserBtn = new JButton("New User");
	private JButton logInBtn = new JButton("Log in");
	
	private ButtonListener btnListener = new ButtonListener();
	
	private ArrayList<GUIListener> listeners = new ArrayList<>(); 
	
	
	public SaveGUI() {
		initializeGUI();
		showWindow();
	}
	
	public void initializeGUI() {
		setVisible(true);
		setOpaque(true);
		setPreferredSize(new Dimension(300, 200));
		setLayout(new BorderLayout());
		
		centerPanel.setLayout(new GridLayout(2, 2));
		
		centerPanel.add(nameLabel);
		centerPanel.add(nameField);
		
		centerPanel.add(passwordLabel);
		centerPanel.add(passwordField);
		
		
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		
		newUserBtn.addActionListener(btnListener);
		logInBtn.addActionListener(btnListener);
		
		buttonPanel.add(newUserBtn);
		buttonPanel.add(logInBtn);
		
		add(centerPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	public void showWindow() {
		windowObject.add(this);
		windowObject.pack();
		windowObject.setVisible(true);
		windowObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void addNewListener(GUIListener listener) {
		listeners.add(listener);
	}
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == newUserBtn) {
				
				User newUser = new User(nameField.getText(), passwordField.getText());
				
				for(int i = 0; i < listeners.size(); i++) {
					
					if(listeners.get(i).controlUser(newUser) == 0) {
						listeners.get(i).saveNewUser(newUser);
						System.out.println("Ny användare tillagd");
						//Låt användare logga in efter detta
					}
					else {
						System.out.println("Användare finns redan");
					}
					
				}
			}
			
			if(e.getSource() == logInBtn) {
				
				
				for(int i = 0; i < listeners.size(); i++) {
					
					if(listeners.get(i).logInUser(nameField.getText(), passwordField.getText()) == 1) {
						System.out.println("Inloggning lyckad");
						//Låt användare logga in efter detta!
					}
					else {
						System.out.println("Felaktigt användarnamn och/eller fel lösenord");
					}
					
				}
				
			}
			
			
		}
		
	}
	
	
//	public static void main(String [] args) {
//		SaveGUI test = new SaveGUI();
//		test.addNewListener(new SavedUsers("files/savedUsersTest.dat"));
//	}
	

}
