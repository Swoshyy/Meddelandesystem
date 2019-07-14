package sethTestGUIs;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import GUI.MessageWindow;
import client.Client;
import client.ClientController;
import saveUsers.SavedUsers;
import user.User;

/**
 * SignUpScreen combined with the saveObjects class to see if a new user can be
 * created (or if there is already a user with the same username)
 * 
 * @author aevanDino, sethoberg
 *
 */

public class SignUpScreen
{
	private JFrame frame;
	private JTextField textField;
	private JButton btnChooseProfilePicture;

	private JFileChooser imgChooser = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("Bilder", "jpg", "png");

	private JLabel lblProfilePicture;
	private ImageIcon profilePicture;
	private ClientController controller;

	private JButton btnSignUp;

	private ButtonListener btnListener = new ButtonListener();
	private SavedUsers savedUsers;

	/**
	 * Create the application.
	 */
	public SignUpScreen(ClientController controller)
	{
		this.controller = controller;
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					initialize();
					addAcceptedImageFilters();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void addSavedUsersObject(SavedUsers savedUsers)
	{
		this.savedUsers = savedUsers;
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void addAcceptedImageFilters()
	{
		imgChooser.setFileFilter(filter);
		imgChooser.addChoosableFileFilter(new FileNameExtensionFilter(null, "jpg"));
		imgChooser.addChoosableFileFilter(new FileNameExtensionFilter(null, "png"));
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 275, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setFont(new Font("Georgia", Font.PLAIN, 32));
		lblSignUp.setBounds(77, 25, 125, 43);
		frame.getContentPane().add(lblSignUp);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Georgia", Font.BOLD, 12));
		lblUsername.setBounds(25, 90, 89, 21);
		frame.getContentPane().add(lblUsername);

		textField = new JTextField();
		textField.setFont(new Font("Georgia", Font.PLAIN, 12));
		textField.setBounds(25, 109, 210, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		lblProfilePicture = new JLabel("");
		lblProfilePicture.setBounds(25, 189, 60, 61);
		lblProfilePicture.setIcon(new ImageIcon(
				new ImageIcon("images/defaultImage.jpg").getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH)));
		frame.getContentPane().add(lblProfilePicture);

		btnChooseProfilePicture = new JButton("Open Images");
		btnChooseProfilePicture.setFont(new Font("Georgia", Font.PLAIN, 12));
		btnChooseProfilePicture.setBounds(95, 205, 140, 23);
		frame.getContentPane().add(btnChooseProfilePicture);

		btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("Georgia", Font.PLAIN, 12));
		btnSignUp.setBounds(25, 261, 210, 23);
		frame.getContentPane().add(btnSignUp);

		btnSignUp.addActionListener(btnListener);
		btnChooseProfilePicture.addActionListener(btnListener);
	}

	private class ButtonListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{

			if (e.getSource() == btnChooseProfilePicture)
			{
				int hej = imgChooser.showOpenDialog(null);
				if (hej == JFileChooser.APPROVE_OPTION)
				{
					profilePicture = new ImageIcon(new ImageIcon(imgChooser.getSelectedFile().getPath()).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
					lblProfilePicture.setIcon(profilePicture);
				}
			}

			if (e.getSource() == btnSignUp)
			{
				User user = new User(profilePicture, textField.getText());
				MessageWindow msgWindow = new MessageWindow(controller, user);
				controller.newUser(user);
				controller.setMessageGUI(msgWindow);
				frame.dispose();
			}

		}

	}

}
