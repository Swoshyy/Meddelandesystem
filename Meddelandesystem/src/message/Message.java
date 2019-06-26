package message;

import java.io.Serializable;
import javax.swing.ImageIcon;

import user.User;

public class Message implements Serializable
{
	private String text;
	private ImageIcon image;
	private User user;
	
	// Lägg till motagarlista

	public Message(String inText)
	{
		this.text = inText;
	}

	public Message(ImageIcon inImage)
	{
		this.image = inImage;
	}

	public Message(String inText, ImageIcon inImage)
	{
		this.text = inText;
		this.image = inImage;
	}
	
	public String getText()
	{
		return text;
	}
	
	public ImageIcon getImage()
	{
		return image;
	}
	
}
