package sethTestGUIs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import saveUsers.SaveObjects;

/**
 * 
 * @author aevanDino, sethoberg
 *
 */

public class LoginScreen
{
	private JFrame frame;
	
	/* Textfields */
	private JTextField textField;
	private JPasswordField passwordField;
	
	/* Buttons */
	private JButton btnSignUp;
	private JButton btnLogin;
	
	private ButtonListener btnListener = new ButtonListener();
	
	private SignUpScreen signUp;
	
	private SaveObjects savedUsers = new SaveObjects("files/savedUsers.txt");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					LoginScreen window = new LoginScreen();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginScreen()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(250, 128, 114, 10));
		frame.setBounds(100, 100, 260, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		textField = new JTextField();
		textField.setFont(new Font("Georgia", Font.PLAIN, 12));
		textField.setBounds(50, 77, 150, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(new Color(0, 0, 0));
		lblUsername.setFont(new Font("Georgia", Font.BOLD, 12));
		lblUsername.setBounds(50, 55, 75, 21);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Georgia", Font.BOLD, 12));
		lblPassword.setBounds(50, 113, 75, 21);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(50, 133, 150, 25);
		frame.getContentPane().add(passwordField);
		
		JLabel lblUser = new JLabel("New User?");
		lblUser.setFont(new Font("Georgia", Font.BOLD, 12));
		lblUser.setBounds(90, 224, 75, 21);
		frame.getContentPane().add(lblUser);
		
		btnSignUp = new JButton("Sign up");
		btnSignUp.setFont(new Font("Georgia", Font.PLAIN, 12));
		btnSignUp.setBounds(75, 256, 100, 23);
		frame.getContentPane().add(btnSignUp);
		
		btnLogin= new JButton("Login");
		btnLogin.setFont(new Font("Georgia", Font.PLAIN, 12));
		btnLogin.setBounds(75, 169, 100, 23);
		frame.getContentPane().add(btnLogin);
		
		btnSignUp.addActionListener(btnListener);
		btnLogin.addActionListener(btnListener);
	}
	
	
	private class ButtonListener implements ActionListener {

		
		public void actionPerformed(ActionEvent e) {
			

			if(e.getSource() == btnLogin) {
					
					if(savedUsers.logInUser(textField.getText(), new String(passwordField.getPassword())) == 1) {
						System.out.println("Inloggning lyckad");
						//Låt användare logga in efter detta
					}
					else {
						System.out.println("Felaktigt användarnamn och/eller fel lösenord");
					}

				

			}
			
			if(e.getSource() == btnSignUp) {
				System.out.println("Nu ska nytt fönster öppnas");
				signUp = new SignUpScreen();
				signUp.addSavedUsersObject(savedUsers);
			}
			
		}
		
	}
	
}
