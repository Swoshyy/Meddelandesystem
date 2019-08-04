package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import GUI.MessageWindow;
import message.Message;
import message.MessageQueue;
import login.LoginStatus;
import sethTestGUIs.LoginScreen;
import user.User;

public class Client {
	private Socket socket;
	private String address;
	private int port;

	private ObjectOutputStream oos;

	private ClientController controller;
	private MessageWindow messageWindow;
	private MessageWriter msgWriter;
	private MessageReader msgReader;
	private User clientUser;

	public Client(String inAddress, int inPort, ClientController inController)
	{
		this.address = inAddress;
		this.port = inPort;
		this.controller = inController;

		connect();

		controller.newClient(this);
	}

	public void connect() {
		try {
			socket = new Socket(address, port);

			oos = new ObjectOutputStream(socket.getOutputStream());

			msgWriter = new MessageWriter();
			msgReader = new MessageReader();
			msgReader.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNewUser(User user) {
		this.clientUser = user;
	}

	public User getUser() {
		return clientUser;
	}

	public void sendObject(Object obj) {
		msgWriter.newMess(obj);
	}

	private class MessageWriter {
		private MessageQueue<Object> msgQueue;
		private Object sendObject;

		public MessageWriter() {
		}

		public void newMess(Object object) {
			this.sendObject = object;
			write();
		}

		public void write() {
			try {
				oos.writeObject(sendObject);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class MessageReader extends Thread {
		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				while (true) {
					Object object = (Object) ois.readObject();

					if (object instanceof Message) {

						Message mess = (Message) object;
						System.out.println("MEssage read from client");
						controller.displayMessage(mess);

					}

					else if (object instanceof LoginStatus) {

						LoginStatus status = (LoginStatus) object;

						if (status.getLoginStatus() == 1) {
							clientUser = status.getLoggedInUser();
							System.out.println("Inloggning lyckad");
							messageWindow = new MessageWindow(controller, clientUser);
//							controller.setMessageGUI(messageWindow);
							controller.setGUI(messageWindow);
							oos.writeObject(status);
							oos.flush();
//							RequestList getListOfUsers = new RequestList(1);
						} else if (status.getLoginStatus() == 0) {
							System.out.println("Felaktigt användarnamn och/eller lösenord");
						}

					}

					else if (object instanceof LinkedList<?>) {
						@SuppressWarnings("unchecked")
						LinkedList<User> users = (LinkedList<User>) object;
						for (User user : users) {
							if(!user.getName().equals(clientUser.getName())) {
								controller.addNewUser(user);
							}
						}

					} else if (object instanceof User) {
						User newLoggedInUser = (User)object;
						controller.addNewUser(newLoggedInUser);
					} 

					else {
						System.out.println("objekt kan inte typkonverteras");
					}

				}
			} catch (IOException | ClassNotFoundException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
