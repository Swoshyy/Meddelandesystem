package user;

import java.util.LinkedList;
import javax.swing.ImageIcon;

public class User
{
	private String name;
	private ImageIcon imgProfilePicture;
	private LinkedList<User> friends;

	public User(String inName)
	{
		this.name = inName;
	}

	public User(String inName, ImageIcon inProfilePicture)
	{
		this.name = inName;
		this.imgProfilePicture = inProfilePicture;
	}
	
	public void addFriend(User inFriend)
	{
		friends.add(inFriend);
	}

	public String getName()
	{
		return name;
	}

	public ImageIcon getImgProfilePicture()
	{
		return imgProfilePicture;
	}

	public LinkedList<User> getFriends()
	{
		return friends;
	}

}
