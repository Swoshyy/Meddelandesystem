package saveUsers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 
 * @author sethoberg
 *
 */

public class SaveObjects implements GUIListener, Serializable {
	
	private static final long serialVersionUID = 4833368582044573121L;
	private SaveGUI saveGUI = new SaveGUI();
	private String fileName; 
	
	
	public SaveObjects(String fileName) {
		saveGUI.addNewListener(this);
		this.fileName = fileName;
	}
	
	
	
	/**
	 * When creating a new user, if a user with the same username exists this method returns 1, 
	 * meaning a user with the same name already exists
	 */
	public int controlUser(User user) {
		//Läs igenom användare som finns
		User tempUser;
		boolean notEmpty = true; 
		int userExists = 0;
		
		
		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
    			new FileInputStream(fileName)))) {
			
			
			//Fixa så att ett objekt läses in först och kontrolleras sedan körs while
			//Loopen sålänge readObject inte är null
			//Om första readObject är null finns inget i filen och inget behövs läsas
//			tempUser = (User) ois.readObject(); 
			while(notEmpty) {
				
				try {
					tempUser = (User) ois.readObject();
					
					if(tempUser.getName().equals(user.getName()) ) {
						userExists = 1;
					}
					
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
		
		return userExists;
	}
	
	
	
	/**
	 * If the name and password matches a user 1 is returned
	 */
	public int logInUser(String name, String password) {
		boolean notEmpty = true;
		User tempUser;
		int logInSuccessfull = 0;
		
		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
    			new FileInputStream(fileName)))) {
			
			
			while(notEmpty) {
				
				try {
					tempUser = (User) ois.readObject();
					
					if(tempUser.checkPassword(name, password) == 1) {
						logInSuccessfull = 1;
					}
					
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
		
		return logInSuccessfull;
		
	}
	

	public void saveNewUser(User user) {
		//Spara ny användare till fil
		
		try(ObjectOutputStream oos = new ObjectOutputStream( new BufferedOutputStream(
    			new FileOutputStream(fileName)))) {
			
			
			oos.writeObject(user);
			oos.flush();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String [] args) {
		SaveObjects test = new SaveObjects("files/savedUsers.txt"); 
	}


	

}
