package displayUsersTest;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


/**
 * 
 * @author sethoberg
 *
 */

public class UsersGUI extends JPanel {
	private JFrame frame = new JFrame("Test användare");
	private DefaultListModel<User> listModel = new DefaultListModel<User>();
	private JList<User> listOfUsers;
	
	
	//Testobjects, remove later
	private User user1 = new User(new ImageIcon(new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User1");
	private User user2 = new User(new ImageIcon(new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User2");
	private User user3 = new User(new ImageIcon(new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User3");
	private User user4 = new User(new ImageIcon(new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User4");
	private User user5 = new User(new ImageIcon(new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User5");
	private User user6 = new User(new ImageIcon(new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User6");
	private User user7 = new User("User7");
	
	
	private JScrollPane userScroll;
	
	
	public UsersGUI() {
//		setPreferredSize(new Dimension(200, 200));
		
		listModel.addElement(user1);
		listModel.addElement(user2);
		listModel.addElement(user3);
		listModel.addElement(user4);
		listModel.addElement(user5);
		listModel.addElement(user6);
		listModel.addElement(user7);
		
		listOfUsers = new JList<User>(listModel);
		listOfUsers.setCellRenderer(new ImageUserRenderer());
		
		
		userScroll = new JScrollPane(listOfUsers);
		userScroll.setPreferredSize(new Dimension(200, 300));
		userScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		userScroll.setAutoscrolls(true);
		
		add(userScroll);
		
		
		
		frame.add(this);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	//Function that may be needed later when first opening program?
	//Display all users both non-friends and friends 
	public void initializeUserList(ArrayList<User> users) {
		
		for(int i = 0; i < users.size(); i++) {
			listModel.addElement((User) users.get(i));
		}
		
		listOfUsers.getModel(); //Updating gui list
		
	}
	
	//Change UserObject in UserGUI to match User in the saveUsers package and rest of system?
	public User getChosenUser() {

		return listOfUsers.getSelectedValue();

	}
	
	public boolean userIsNotSelected() {
		
		return listOfUsers.isSelectionEmpty();
	}
	
	
	public void addNewUser(User newUser) {
		listModel.addElement(newUser);
		listOfUsers.getModel();
		
		userScroll.revalidate();
		userScroll.repaint();
	}
	
	public void removeUser(User oldUser) {
		
		if(listOfUsers.getSelectedIndex() >= 0) {
			listModel.removeElementAt(listOfUsers.getSelectedIndex());
			listOfUsers.getModel();
			
			userScroll.revalidate();
			userScroll.repaint();
		}
		
	}
	
	
	/**
	 * For adding multiple users when starting application 
	 * for example adding all online users or all saved friends
	 * @param userList
	 */
	public void addListOfUsers(LinkedList<User> userList) {

		for(int i = 0; i < userList.size(); i++) {
			listModel.addElement(userList.get(i));
		}
		listOfUsers.getModel();

	}
	
	
	
	
	public static void main(String [] args) {
		UsersGUI testObject = new UsersGUI();
	}
	
}
