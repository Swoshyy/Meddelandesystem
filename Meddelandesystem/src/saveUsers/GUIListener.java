package saveUsers;

/**
 * 
 * @author sethoberg
 *
 */

public interface GUIListener {
	
	public int controlUser(User user); 
	
	public int logInUser(String name, String password);
	
	public void saveNewUser(User user);
	
}
