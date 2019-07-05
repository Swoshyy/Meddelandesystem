package displayUsersTest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import user.User;


/**
 * Gui showing a list of both all online users and a list of saved contacts/friends
 * The GUI also has buttons for adding or removing a user from their friendlist, sending a 
 * message to a selected user and logging out
 * @author sethoberg
 *
 */

public class AllUsersGUI extends JPanel {
	private JFrame frame;
	
	private JPanel allUsersPanel = new JPanel();
	
	private SavedFriends saveFriendsObject = new SavedFriends("files/savedFriends.txt");
	
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
	
	
	/**
	 * Showing gui in frame
	 */
	public void showFrame() {
		frame = new JFrame("Users gui");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();
		
	}
	
	
	/**
	 * Method for creating the whole gui
	 */
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
	
	
	
	/**
	 * Method for adding a list of users to the online users list in the gui
	 * @param userList
	 */
	public void addListOfOnlineUsers(LinkedList<User> userList) {
		
		onlineUsers.addListOfUsers(userList);

	}
	
	
	/**
	 * Method for adding a list of friends to the friend list in the gui
	 * @param userList
	 */
	public void addListOfSavedFriends(LinkedList<User> userList) {

		friends.addListOfUsers(userList);

	}
	
	
	/**
	 * Inner class for button clicks
	 * @author sethoberg
	 *
	 */
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			
			/**
			 * Add friend if they are not already a friend or a user is not selected
			 */
			if(e.getSource() == addFriendBtn) {
				
				if(onlineUsers.userIsNotSelected() == false) {
					saveFriendsObject.updateListOfFriends();
					friends.addNewUser(onlineUsers.getChosenUser());
					saveFriendsObject.saveNewFriend(onlineUsers.getChosenUser());
					onlineUsers.removeUser(onlineUsers.getChosenUser());
				}
				else {
					System.out.println("Kan inte lägga till någon som redan är en vän/du har inte valt en användare");
				}
				
			}
			
			/**
			 * Add friend if they are not already a friend or a user is not selected
			 */
			if(e.getSource() == removeFriendBtn) {
				if(friends.userIsNotSelected() == false) {
					onlineUsers.addNewUser(friends.getChosenUser());
					
					saveFriendsObject.updateListOfFriends();
					saveFriendsObject.removeFriendFromList(friends.getChosenUser());
					saveFriendsObject.saveNewFriendList();
					
					
					friends.removeUser(friends.getChosenUser());
				}
				else {
					System.out.println("Kan inte ta bort en vän som inte är en vän/du har inte valt en användare");
				}
			}
			if(e.getSource() == messageBtn) {
				System.out.println("Cannot send message yet");
			}
			
			if(e.getSource() == logOutBtn) {
				System.out.println("Log out user here and send them back to the log in screen");
//				System.exit(0); 
			}
			
			
		}
		
	}
	
	
	/**
	 * Used for testing, Remove later
	 * @param args
	 */
	public static void main(String [] args) {
		AllUsersGUI testObject = new AllUsersGUI();
		
		LinkedList<User> testUsers = new LinkedList<User>(); 
		testUsers.add(new User(new ImageIcon(new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User1"));
		testUsers.add(new User(new ImageIcon(new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User2"));
		testUsers.add(new User(new ImageIcon(new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User3"));
		testUsers.add(new User(new ImageIcon(new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User4"));
		testUsers.add(new User(new ImageIcon(new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User5"));
		testUsers.add(new User(new ImageIcon(new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User6"));
		testUsers.add(new User("User7"));
		
		testObject.addListOfOnlineUsers(testUsers);
		testObject.addListOfSavedFriends(testUsers);
	}

}
