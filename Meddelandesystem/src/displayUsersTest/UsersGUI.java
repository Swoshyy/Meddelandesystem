package displayUsersTest;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;

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
	private DefaultListModel<UserObject> listModel = new DefaultListModel<UserObject>(); 
	private JList<UserObject> listOfUsers;
	
	
	//Testobjects, remove later
	private UserObject user1 = new UserObject(new ImageIcon(new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User1");
	private UserObject user2 = new UserObject(new ImageIcon(new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User2");
	private UserObject user3 = new UserObject(new ImageIcon(new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User3");
	private UserObject user4 = new UserObject(new ImageIcon(new ImageIcon("images/blue.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User4");
	private UserObject user5 = new UserObject(new ImageIcon(new ImageIcon("images/orange.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User5");
	private UserObject user6 = new UserObject(new ImageIcon(new ImageIcon("images/magenta.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)), "User6");
	private UserObject user7 = new UserObject("User7");
	
	
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
		
		listOfUsers = new JList<UserObject>(listModel);
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
	public void initializeUserList(ArrayList<UserObject> users) {
		
		for(int i = 0; i < users.size(); i++) {
			listModel.addElement((UserObject) users.get(i));
		}
		
		listOfUsers.getModel(); //Updating gui list
		
	}
	
	//Change UserObject in UserGUI to match User in the saveUsers package and rest of system?
	public UserObject getChosenUser() {

		return listOfUsers.getSelectedValue();

	}
	
	public boolean userIsNotSelected() {
		
		return listOfUsers.isSelectionEmpty();
	}
	
	
	public void addNewUser(UserObject newUser) {
		listModel.addElement(newUser);
		listOfUsers.getModel();
		
		userScroll.revalidate();
		userScroll.repaint();
	}
	
	public void removeUser(UserObject oldUser) {
		
		if(listOfUsers.getSelectedIndex() >= 0) {
			listModel.removeElementAt(listOfUsers.getSelectedIndex());
			listOfUsers.getModel();
			
			userScroll.revalidate();
			userScroll.repaint();
		}
		
	}
	
	
	
	
	public static void main(String [] args) {
		UsersGUI testObject = new UsersGUI();
	}
	
}
