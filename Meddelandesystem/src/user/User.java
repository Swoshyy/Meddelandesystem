package user;

import java.awt.Image;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Complete user class!
 * @author sethoberg
 *
 */

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3061787204562448072L;
	private ImageIcon image;
	private String name;
	private String password;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean isOnlione;
	
	
	public User(ImageIcon image, String name, String password) {
		this.image = image;
		this.name = name;
		this.password = password;
	}
	
	public User(ImageIcon image, String name) {
		this.image = image;
		this.name = name;
	}
	
	public User(String name) {
		image = new ImageIcon(new ImageIcon("images/defaultImage.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		this.name = name;
	}
	
	public User(String name, String password) {
		image = new ImageIcon(new ImageIcon("images/defaultImage.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		this.name = name;
		this.password = password;
	}
	
	
	public String toString() {
		return name;
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public int checkPassword(String name, String password) {
		
		if (this.name.equals(name) && this.password.equals(password)) {
			return 1;
		}
		else {
			return 0;
		}
		
	}
	
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	
	public ObjectOutputStream getOos() {
		return oos;
	}
	
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	
	public ObjectInputStream getOis() {
		return ois;
	}

	public boolean getIsOnlione() {
		return isOnlione;
	}

	public void setIsOnlione(boolean isOnlione) {
		this.isOnlione = isOnlione;
	}
	
}
