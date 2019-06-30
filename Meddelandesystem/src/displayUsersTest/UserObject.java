package displayUsersTest;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author sethoberg
 *
 */

public class UserObject {
	private ImageIcon image;
	private String name;
	
	public UserObject(ImageIcon image, String name) {
		this.image = image;
		this.name = name;
	}
	
	public UserObject(String name) {
		image = new ImageIcon(new ImageIcon("images/defaultImage.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		this.name = name;
	}
	
	
	public String toString() {
		return name;
	}
	
	public ImageIcon returnImageUrl() {
		return image;
	}
	
	
}
