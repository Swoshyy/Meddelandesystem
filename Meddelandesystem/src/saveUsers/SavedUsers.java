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
import java.util.LinkedList;

import user.User;

/**
 * Class for saving new users, the saved users txt file should be stored on the
 * server
 * 
 * @author sethoberg
 *
 */

public class SavedUsers implements Serializable {

	private static final long serialVersionUID = 4833368582044573121L;
	private String fileName;
	private LinkedList<User> savedUsers = new LinkedList<>();
	private int returnUserIndex = 0;

	public SavedUsers(String fileName) {
		this.fileName = fileName;

		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {

			boolean hasNext = true;

			while (hasNext) {
				try {
					@SuppressWarnings("unchecked")
					LinkedList<User> users = (LinkedList<User>) ois.readObject();
					savedUsers = users;

				} catch (EOFException e) {
					hasNext = false;
				}
			}
			for(User user : savedUsers) {
				user.setIsOnline(false);
			}
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * When creating a new user, if a user with the same username exists this method
	 * returns 1, meaning a user with the same name already exists
	 */
	public int controlUser(User user) {
		// Läs igenom användare som finns
		boolean notEmpty = true;
		int userExists = 0;

		for (User savedUser : savedUsers) {
			if (savedUser.getName().equals(user.getName())) {
				userExists = 1;
			}
		}
//		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
//    			new FileInputStream(fileName)))) {
//			
//			while(notEmpty) {
//				
//				try {
//					
//					savedUsers = (LinkedList<User>) ois.readObject();
//					
//					for(int i = 0; i < savedUsers.size(); i++) {
//						if(savedUsers.get(i).getName().equals(user.getName()) ) {
//							userExists = 1;
//						}
//					}
//		
//					
//				} catch(EOFException e) {
//					notEmpty = false;
//				}
//				
//				
//			}
//			
//			
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}

		return userExists;
	}

	/**
	 * If the name and password matches a user 1 is returned If either the name or
	 * password (or both) do not match a user 0 is returned meaning a user cannot
	 * log in
	 */
	public int logInUser(String name, String password) {
		boolean notEmpty = true;
		int logInSuccessfull = 0;

		for (User user : savedUsers) {
			if (user.checkPassword(name, password) == 1) {
				logInSuccessfull = 1;
				break;
			}
		}
//		try(ObjectInputStream ois = new ObjectInputStream( new BufferedInputStream(
//    			new FileInputStream(fileName)))) {
//			
//			
//			while(notEmpty) {
//				
//				try {
//					
//					savedUsers = (LinkedList<User>) ois.readObject();
//					
//					for(int i = 0; i < savedUsers.size(); i++) {
//						if(savedUsers.get(i).checkPassword(name, password) == 1) {
//							logInSuccessfull = 1;
//							returnUserIndex = i;
//						}
//					}
//					
//					
//				} catch(EOFException e) {
//					notEmpty = false;
//				}
//				
//				
//			}
//			
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}

		return logInSuccessfull;

	}

	
	public LinkedList<User> getUsers(){
		return savedUsers;
	}
	
	/**
	 * Kommer bli en bugg För att fungera felfritt måste hela listan läsas av på
	 * nytt och ha ett user objekt som parameter så man får
	 * 
	 * @return
	 */
	public User getUser(String UserName) {
		for(User user : savedUsers) {
			if(user.getName().equals(UserName)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Save a new user to the savedUsers dat file
	 * 
	 * @param user
	 */
	public void saveNewUser(User user) {
		// Spara ny användare till fil

		try (ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(fileName)))) {

//			if(savedUsers == null) {
//				savedUsers = new LinkedList<User>();
//				savedUsers.add(user); 
//			}
//			else {
			savedUsers.add(user);
//			}

			oos.writeObject(savedUsers);
			oos.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Remove later, just for testing
	 */
	public void checkSavedUsers() {
		boolean notEmpty = true;

		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {

			while (notEmpty) {

				try {

					savedUsers = (LinkedList<User>) ois.readObject();

					for (int i = 0; i < savedUsers.size(); i++) {
						System.out.println(savedUsers.get(i).getName() + " " + "password hemligt");
					}

				} catch (EOFException e) {
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
	}

}
