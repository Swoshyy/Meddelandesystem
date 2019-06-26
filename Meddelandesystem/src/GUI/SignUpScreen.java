package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SignUpScreen
{

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnChooseProfilePicture;
	private JButton btnSignUp;

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
					SignUpScreen window = new SignUpScreen();
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
	public SignUpScreen()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 275, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setFont(new Font("Georgia", Font.PLAIN, 32));
		lblSignUp.setBounds(77, 25, 125, 43);
		frame.getContentPane().add(lblSignUp);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Georgia", Font.BOLD, 12));
		lblUsername.setBounds(25, 90, 89, 21);
		frame.getContentPane().add(lblUsername);
		
		JLabel label = new JLabel("Password:");
		label.setFont(new Font("Georgia", Font.BOLD, 12));
		label.setBounds(25, 140, 89, 21);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setFont(new Font("Georgia", Font.PLAIN, 12));
		textField.setBounds(25, 109, 210, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Georgia", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(25, 158, 210, 20);
		frame.getContentPane().add(textField_1);
		
		JLabel lblProfilePicture = new JLabel("");
		lblProfilePicture.setBounds(25, 189, 60, 61);
		lblProfilePicture.setIcon(new ImageIcon(new ImageIcon("images/defaultProfilePicture.png").getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH)));
		frame.getContentPane().add(lblProfilePicture);
		
		btnChooseProfilePicture = new JButton("Open Images");
		btnChooseProfilePicture.setFont(new Font("Georgia", Font.PLAIN, 12));
		btnChooseProfilePicture.setBounds(95, 205, 140, 23);
		frame.getContentPane().add(btnChooseProfilePicture);
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("Georgia", Font.PLAIN, 12));
		btnSignUp.setBounds(25, 261, 210, 23);
		frame.getContentPane().add(btnSignUp);
	}

}
