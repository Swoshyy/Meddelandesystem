package message;

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
import java.util.LinkedList;

import user.User;

public class SavedMessages implements Serializable  {


	private static final long serialVersionUID = 6333619776436792380L;
	private String fileName; 
	private LinkedList<Message> savedMessages;
	
	
	public SavedMessages(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	/**
	 * When creating a new user, if a user with the same username exists this method returns 1, 
	 * meaning a user with the same name already exists
	 */
	 
	public int messageForUserExists(User reciever) {
		//Läs igenom användare som finns
		boolean notEmpty = true; 
		int messageSaved = 0;
		
		
		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
    			new FileInputStream(fileName)))) {
			
			while(notEmpty) {
				
				try {
					
					savedMessages = (LinkedList<Message>) ois.readObject();
					
				for(int i = 0; i < savedMessages.size(); i++) {
					if(savedMessages.get(i).getReceiver().getName().equals(reciever.getName()) ) {
						messageSaved = 1;
						}
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
		
		return messageSaved;
	}
	
	
	
//	/**
//	 * If the name and password matches a user 1 is returned
//	 */
	public LinkedList<Message> getSavedMessages(User reciever) {
		boolean notEmpty = true;
		LinkedList<Message> savedMessagesList = new LinkedList<>();
		
		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
    			new FileInputStream(fileName)))) {
			
			
			while(notEmpty) {
				
				try {
					
					savedMessages = (LinkedList<Message>) ois.readObject();
					
					for(int i = 0; i < savedMessages.size(); i++) {
						if(savedMessages.get(i).getReceiver().getName().equals(reciever.getName())) {
//							return savedMessages.get(i);
							savedMessagesList.add(savedMessages.get(i));
							savedMessages.remove(i);
						}
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
		
		return savedMessagesList;
		
	}
	

	public void saveNewMessage(Message newMessage) {
		//Spara ny användare till fil
		
		try(ObjectOutputStream oos = new ObjectOutputStream( new BufferedOutputStream(
    			new FileOutputStream(fileName)))) {
			
			if(savedMessages == null) {
				savedMessages = new LinkedList<Message>();
				savedMessages.add(newMessage); 
			}
			else {
				savedMessages.add(newMessage); 
			}
			
			
			oos.writeObject(savedMessages);
			oos.flush();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	


}
