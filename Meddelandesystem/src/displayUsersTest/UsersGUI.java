package displayUsersTest;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import user.User;


/**
 * GUI for showing a list of users, two UsersGUI are used in the AllUsersGUI, one list for online users and one for friends
 * @author sethoberg
 *
 */

public class UsersGUI extends JPanel {
//	private JFrame frame = new JFrame("Test användare");
	private DefaultListModel<User> listModel = new DefaultListModel<User>();
	private JList<User> listOfUsers;
	
	private JScrollPane userScroll;
	
	
	public UsersGUI() {

		listOfUsers = new JList<User>(listModel);
		listOfUsers.setCellRenderer(new ImageUserRenderer());
		
		
		userScroll = new JScrollPane(listOfUsers);
		userScroll.setPreferredSize(new Dimension(200, 600));
		userScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		userScroll.setAutoscrolls(true);
		
		add(userScroll);
		
	}
	
	
	
	/**
	 * Method used when adding multiple users to the list of either online users or friends
	 * @param userList
	 */
	public void addListOfUsers(LinkedList<User> userList) {

		for(int i = 0; i < userList.size(); i++) {
			listModel.addElement(userList.get(i));
		}
		listOfUsers.getModel();

	}
	
	
	/**
	 * Get selected user chosen 
	 * @return
	 */
	public User getChosenUser() {

		return listOfUsers.getSelectedValue();

	}
	
	
	/**
	 * Returns true if a user is Not selected
	 * @return
	 */
	public boolean userIsNotSelected() {
		
		return listOfUsers.isSelectionEmpty();
	}
	
	
	/**
	 * Add user to list
	 * @param newUser
	 */
	public void addNewUser(User newUser) {
		listModel.addElement(newUser);
		listOfUsers.getModel();
		
		userScroll.revalidate();
		userScroll.repaint();
	}
	
	
	/**
	 * Remove user from list
	 * @param newUser
	 */
	public void removeUser(User oldUser) {
		
		if(listOfUsers.getSelectedIndex() >= 0) {
			listModel.removeElementAt(listOfUsers.getSelectedIndex());
			listOfUsers.getModel();
			
			userScroll.revalidate();
			userScroll.repaint();
		}
		
	}
	
//	public static void main(String [] args) {
//		UsersGUI testObject = new UsersGUI();
//		
//	}
	
}
