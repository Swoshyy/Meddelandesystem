package message;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class Message implements Serializable
{
	private String message;
	private ImageIcon image;

	public Message(String inMessage)
	{
		this.message = inMessage;
	}

	public Message(ImageIcon inImage)
	{
		this.image = inImage;
	}

	public Message(String inMessage, ImageIcon inImage)
	{
		this.message = inMessage;
		this.image = inImage;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public ImageIcon getImage()
	{
		return image;
	}
	
}
