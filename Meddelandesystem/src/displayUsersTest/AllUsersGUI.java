package displayUsersTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import user.User;

/**
 * Gui showing a list of both all online users and a list of saved
 * contacts/friends The GUI also has buttons for adding or removing a user from
 * their friendlist, sending a message to a selected user and logging out
 * 
 * !!! In main method is a test list of user which should later be removed!
 * 
 * @author sethoberg
 *
 */

public class AllUsersGUI extends JPanel
{
	private JFrame frame;

	private JPanel allUsersPanel = new JPanel();

	private SavedFriends saveFriendsObject = new SavedFriends("files/savedFriends.dat");

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

	private LinkedList<User> testUsers = new LinkedList<User>();

	private ButtonListener btnListener = new ButtonListener();

	public AllUsersGUI()
	{
		initializeGUI();
	}

	public void addUser(User user)
	{
			testUsers.add(new User(new ImageIcon(user.getImage().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), user.getName()));
			addListOfOnlineUsers(testUsers);
			
	}

	public void addUsers(ArrayList<User> users)
	{
		for(int i=0; i<users.size(); i++)
		{
			if(!testUsers.contains(users.get(i)))
			{
				testUsers.add(new User(new ImageIcon(users.get(i).getImage().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), users.get(i).getName()));				
			}
		}
	}

	/**
	 * Method for creating the whole gui
	 */
	public void initializeGUI()
	{
		setLayout(new BorderLayout());

		allUsersPanel.setLayout(new GridLayout(2, 2));
		allUsersPanel.setPreferredSize(new Dimension(200, 600));
		allUsersPanel.setBackground(new Color(252, 137, 114));

		onlineUsersPanel.setLayout(new BorderLayout());
		onlineUsersPanel.add(onlineUsersLabel, BorderLayout.NORTH);
		onlineUsersPanel.add(onlineUsers, BorderLayout.CENTER);

		friendsPanel.setLayout(new BorderLayout());
		friendsPanel.setPreferredSize(new Dimension(200, 600));
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
	 * 
	 * @param userList
	 */
	public void addListOfOnlineUsers(LinkedList<User> userList)
	{
		onlineUsers.addListOfUsers(userList);
		new UsersGUI();
	}

	/**
	 * Method for adding a list of friends to the friend list in the gui
	 * 
	 * @param userList
	 */
	public void addListOfSavedFriends(LinkedList<User> userList)
	{
		friends.addListOfUsers(userList);
	}

	/**
	 * Inner class for button clicks
	 * 
	 * @author sethoberg
	 *
	 */

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			/**
			 * Add friend if they are not already a friend or a user is not selected
			 */
			if (e.getSource() == addFriendBtn)
			{

				if (onlineUsers.userIsNotSelected() == false)
				{
					saveFriendsObject.updateListOfFriends();
					friends.addNewUser(onlineUsers.getChosenUser());
					saveFriendsObject.saveNewFriend(onlineUsers.getChosenUser());
					onlineUsers.removeUser(onlineUsers.getChosenUser());
				} else
				{
					System.out.println(
							"Kan inte lägga till någon som redan är en vän/du har inte valt en användare");
				}

			}

			/**
			 * Add friend if they are not already a friend or a user is not selected
			 */
			if (e.getSource() == removeFriendBtn)
			{
				if (friends.userIsNotSelected() == false)
				{
					onlineUsers.addNewUser(friends.getChosenUser());

					saveFriendsObject.updateListOfFriends();
					saveFriendsObject.removeFriendFromList(friends.getChosenUser());
					saveFriendsObject.saveNewFriendList();

					friends.removeUser(friends.getChosenUser());
				} else
				{
					System.out.println("Kan inte ta bort en vän som inte är en vän/du har inte valt en användare");
				}
			}
			if (e.getSource() == messageBtn)
			{
				System.out.println("Cannot send message yet");
			}

			if (e.getSource() == logOutBtn)
			{
				System.out.println("Log out user here and send them back to the log in screen");
				//				System.exit(0); 
			}

		}

	}

	public static void main(String[] args)
	{
		new AllUsersGUI();
	}

	/*
	 * Remove later
	 */
	public void testUsers()
	{
		LinkedList<User> testUsers = new LinkedList<User>();
		testUsers.add(new User(
				new ImageIcon(
						new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
				"User1"));
		testUsers.add(new User(
				new ImageIcon(
						new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
				"User2"));
		testUsers.add(new User(
				new ImageIcon(
						new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
				"User3"));
		testUsers.add(new User(
				new ImageIcon(
						new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
				"User4"));
		testUsers.add(new User(
				new ImageIcon(
						new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
				"User5"));
		testUsers.add(new User(
				new ImageIcon(
						new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
				"User6"));
		testUsers.add(new User("User7"));

		addListOfOnlineUsers(testUsers);
		addListOfSavedFriends(testUsers);
	}

}
