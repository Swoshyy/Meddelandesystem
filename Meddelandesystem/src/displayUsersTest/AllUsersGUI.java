package displayUsersTest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * @author sethoberg
 *
 */

public class AllUsersGUI extends JPanel {
	private JFrame frame;
	
	private JPanel allUsersPanel = new JPanel();
	
	private JPanel onlineUsersPanel = new JPanel();
	private JLabel onlineUsersLabel = new JLabel("Online");
	private UsersGUI onlineUsers = new UsersGUI();
	
	
	private JPanel friendsPanel = new JPanel();
	private JLabel friendsLabel = new JLabel("Friends");
	private UsersGUI friends = new UsersGUI();
	
	
	private JPanel buttonsPanel = new JPanel();
	private JButton addFriendBtn = new JButton("Add Friend");
	private JButton removeFriendBtn = new JButton("Unfriend");
	private JButton messageBtn = new JButton("Message");
	private JButton logOutBtn = new JButton("Log Out");
	
	private ButtonListener btnListener = new ButtonListener();
	
	public AllUsersGUI() {
		
		initializeGUI();
		showFrame();
		
	}
	
	public void showFrame() {
		frame = new JFrame("Users gui");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();
		
	}
	
	public void initializeGUI() {
		
		setLayout(new BorderLayout());
		
		
		allUsersPanel.setLayout(new GridLayout(2,2));
		
		onlineUsersPanel.setLayout(new BorderLayout());
		onlineUsersPanel.add(onlineUsersLabel, BorderLayout.NORTH);
		onlineUsersPanel.add(onlineUsers, BorderLayout.CENTER);
		
		friendsPanel.setLayout(new BorderLayout());
		friendsPanel.add(friendsLabel, BorderLayout.NORTH);
		friendsPanel.add(friends, BorderLayout.CENTER);
		
		
		allUsersPanel.add(onlineUsersPanel);
		allUsersPanel.add(friendsPanel);
		
		add(allUsersPanel, BorderLayout.CENTER);
		
		buttonsPanel.setLayout(new GridLayout(2, 2));
		
		addFriendBtn.addActionListener(btnListener);
		removeFriendBtn.addActionListener(btnListener);
		messageBtn.addActionListener(btnListener);
		logOutBtn.addActionListener(btnListener);
		
		buttonsPanel.add(addFriendBtn);
		buttonsPanel.add(removeFriendBtn);
		buttonsPanel.add(messageBtn);
		buttonsPanel.add(logOutBtn);
		
		add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == addFriendBtn) {
				
				if(onlineUsers.userIsNotSelected() == false) {
					friends.addNewUser(onlineUsers.getChosenUser());
					onlineUsers.removeUser(onlineUsers.getChosenUser());
				}
				else {
					System.out.println("Kan inte lägga till någon som redan är en vän/du har inte valt en användare");
				}
				
			}
			if(e.getSource() == removeFriendBtn) {
				if(friends.userIsNotSelected() == false) {
					onlineUsers.addNewUser(friends.getChosenUser());
					friends.removeUser(friends.getChosenUser());
				}
				else {
					System.out.println("Kan inte ta bort en vän som inte är en vän/du har inte valt en användare");
				}
			}
			if(e.getSource() == messageBtn) {
				System.out.println("Kan inte skicka meddelande än");
			}
			if(e.getSource() == logOutBtn) {
				System.exit(0); //Change later? Log out should take user back to the log in screen perhaps?
				//Also the logOut button should log out the client from the server
			}
			
			
		}
		
	}
	
	public static void main(String [] args) {
		AllUsersGUI testObject = new AllUsersGUI();
	}

}
