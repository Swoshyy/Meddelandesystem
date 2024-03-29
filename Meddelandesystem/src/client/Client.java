package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

import GUI.MessageWindow;
import message.Message;
import message.MessageQueue;
import server.ActiveClient;
import server.LoginStatus;
import server.UserListHolder;
import sethTestGUIs.LoginScreen;
import user.User;

public class Client
{
	private Socket socket;
	private String address;
//	private InetAddress address;
	private int port; 
	
	private ObjectOutputStream oos;
	
	private ClientController controller;
	private MessageWindow messageWindow;
	private MessageWriter msgWriter;
	private MessageReader msgReader;
	private User clientUser;
	
//	private LoginScreen loginScreen;
	
	public Client(String inAddress, int inPort, ClientController inController)
//	public Client(InetAddress inAddress, int inPort, ClientController inController)
	{
		this.address = inAddress;
		this.port = inPort;
		this.controller = inController;
		
		connect();
		
		controller.newClient(this);
		
		
		
	}
	
	public void connect()
	{
		try
		{
			socket =  new Socket(address, port);
			
			oos = new ObjectOutputStream(socket.getOutputStream());

			
			msgWriter = new MessageWriter();
			msgReader = new MessageReader();
			msgReader.start();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public void setNewUser(User user) {
		this.clientUser = user;
	}
	
	public User getUser() {
		return clientUser;
	}
	
	
	public void sendObject(Object obj)
	{
		msgWriter.newMess(obj);
	}
	
	private class MessageWriter
	{
		private MessageQueue<Object> msgQueue;
		private Object sendObject;
		
		public MessageWriter()
		{
		}
		
		public void newMess(Object object)
		{
			this.sendObject = object;
			write();
		}
		
		public void write()
		{
			try
			{
//				while(true)
//				{
					oos.writeObject(sendObject);
					oos.flush();
					System.out.println("Objekt skickat till server");
//				}
			} catch (IOException e)
			{
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
//				e.printStackTrace();
			}
		}
		
	}
	
	private class MessageReader extends Thread
	{
		public void run()
		{
			try
			{
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				while(true)
				{
					Object object = (Object) ois.readObject();
					
if(object instanceof Message) {
						
						Message mess = (Message) object;
						System.out.println("MEssage read from client");
						controller.displayMessage(mess);
					}
					
					else if(object instanceof LoginStatus) {
						
						LoginStatus status = (LoginStatus) object;
						
						if(status.getLoginStatus() == 1) {
							clientUser = status.getLoggedInUser();
							System.out.println("Inloggning lyckad");
							messageWindow = new MessageWindow(controller);
							controller.setGUI(messageWindow);
							controller.closeLogin();
							
							//Denna del utförs inte?
							RequestList getListOfUsers = new RequestList(1);
//							sendObject(getListOfUsers);
							controller.sendMessage(getListOfUsers);
							
//							controller.closeSignUp(); 
						}
						else if (status.getLoginStatus() == 0) {
							System.out.println("Felaktigt användarnamn och/eller lösenord");
						}
						
					}
					
					
					else if(object instanceof UserListHolder) {
						UserListHolder listHolderObject = (UserListHolder) object;
						LinkedList<ClientConnection> activeUsers = (LinkedList<ClientConnection>) listHolderObject.getUserList();
						LinkedList<User> tempList = new LinkedList<User>();
						System.out.print("Lista mottagen av client");
						for(int i = 0; i < activeUsers.size(); i++) {
							tempList.add(activeUsers.get(i).getUser());
						}
						
						controller.addListOfUsers(tempList);
						
					}
					
					
					else if(object instanceof ClientConnection[]) {
						UserListHolder listHolderObject = (UserListHolder) object;
						LinkedList<ClientConnection> activeUsers = (LinkedList<ClientConnection>) listHolderObject.getUserList();
						LinkedList<User> tempList = new LinkedList<User>();
						System.out.print("Lista mottagen av client");
						for(int i = 0; i < activeUsers.size(); i++) {
							tempList.add(activeUsers.get(i).getUser());
						}
						
						controller.addListOfUsers(tempList);
						
					}
					
					
					else if(object == null) {
						System.out.println("Objekt är null");
					}
					
					else {
						System.out.println("Objekt kan inte typkonverteras");
					}
					
					
					
				}
			} catch(IOException | ClassNotFoundException e)
			{
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
	
}
