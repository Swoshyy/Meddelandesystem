package message;

import java.io.Serializable;
import javax.swing.ImageIcon;

import user.User;

public class Message implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;
	private ImageIcon image;
	private User reciever;
	private User sender;
	// L�gg till motagarlista
	
	private SavedMessages savedMessages = new SavedMessages("files/savedMessages.txt");

	public Message(String inText, User sender, User reciever)
	{
		this.text = inText;
		
		this.sender = sender;
		this.reciever = reciever;
		
		if (reciever.getIsOnlione()==false) {
			
			savedMessages.saveNewMessage(this);
			
		}
		else {
			//Skicka meddelande som vanligt
		}
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
	
	
	public User getSender() {
		return sender;
	}
	
	public User getReceiver() {
		return reciever;
	}
	
}
