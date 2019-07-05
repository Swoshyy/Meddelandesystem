package displayUsersTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import user.User;


/**
 * Class used for saving friends a user adds to a local file (files/savedFriends.txt)
 * @author sethoberg
 *
 */

public class SavedFriends {
	private String fileName; 
	private LinkedList<User> savedFriends;
	
	
	public SavedFriends(String fileName) {
		this.fileName = fileName;
	}
	
	
	/**
	 * Adds a new friend to the txt file
	 * @param newFriend
	 */
	public void saveNewFriend(User newFriend) {
		
		try(ObjectOutputStream oos = new ObjectOutputStream( new BufferedOutputStream(
    			new FileOutputStream(fileName)))) {
			
			if(savedFriends == null) {
				savedFriends = new LinkedList<User>();
				savedFriends.add(newFriend); 
			}
			else {
				savedFriends.add(newFriend); 
			}
			
			
			oos.writeObject(savedFriends);
			oos.flush();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Ny kontakt: " + newFriend.getName() + " Tillagd");
//		printFriendList(); For testing
		
	}
	
	
	/**
	 * Gets an updated version of the friend list after saving a new friend
	 * Updates the savedFriends variable
	 */
	public void updateListOfFriends() {
		boolean notEmpty = true;
		
		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
    			new FileInputStream(fileName)))) {
			
			
			//Fixa så att ett objekt läses in först och kontrolleras sedan körs while
			//Loopen sålänge readObject inte är null
			//Om första readObject är null finns inget i filen och inget behövs läsas
//			tempUser = (User) ois.readObject(); 
			while(notEmpty) {
				
				try {
					
					savedFriends = (LinkedList<User>) ois.readObject();
					System.out.println("Något händer");
					
				} catch(EOFException e) {
					notEmpty = false;
				}
				
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
//		printFriendList(); //For testing 
		
	}
	
	
	
	/**
	 * Returns a list of all saved friends a user has 
	 * @return
	 */
	public LinkedList getListOfFriends() {
		boolean notEmpty = true;
		
		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
    			new FileInputStream(fileName)))) {
			
			
			//Fixa så att ett objekt läses in först och kontrolleras sedan körs while
			//Loopen sålänge readObject inte är null
			//Om första readObject är null finns inget i filen och inget behövs läsas
//			tempUser = (User) ois.readObject(); 
			while(notEmpty) {
				
				try {
					
					savedFriends = (LinkedList<User>) ois.readObject();
					
					
				} catch(EOFException e) {
					notEmpty = false;
				}
				
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return savedFriends;
	}
	
	
	/**
	 * Removes a friend from the list of friends if the list contains a user with the same username 
	 * as the user chosen
	 * @param oldFriend
	 */
	public void removeFriendFromList(User oldFriend) {
		
		
		for(int i = 0; i < savedFriends.size(); i++ ) {
			if(savedFriends.get(i).getName().equals(oldFriend.getName())) {
				savedFriends.remove(savedFriends.get(i));
			}
		}
		
	}
	
	
	/**
	 * Saves the savedFriends variable to the txt file
	 */
	public void saveNewFriendList() {
		
		try(ObjectOutputStream oos = new ObjectOutputStream( new BufferedOutputStream(
    			new FileOutputStream(fileName)))) {
			
			
			oos.writeObject(savedFriends);
			oos.flush();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		printFriendList(); For testing
		
	}
	
	
	/**
	 * Method for testing, remove later
	 */
	public void printFriendList() {
		
		System.out.println("");
		System.out.println("Sparade kontakter");
		
		if(savedFriends != null) {
			for(int i = 0; i < savedFriends.size(); i++) {
				System.out.println(savedFriends.get(i).getName());
			}
		}
		else {
			System.out.println("Inga kontankter sparade");
		}
		
	}
	

}
