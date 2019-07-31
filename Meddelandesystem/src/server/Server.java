package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.SwingUtilities;

import GUI.ServerHistory;
import client.ClientConnection;
import client.LogInObject;
import message.Message;
import saveUsers.SavedUsers;
//import server.Server.WriteMessage;
import user.User;

public class Server {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private ServerController controller;
	private ServerSocket serverSocket;
	private Socket socket;
	private UserWriter userWriter;
	private SavedUsers savedUsers = new SavedUsers("files/SavedUsers.dat");
//	private LinkedList<ActiveClient> connectedClients = new LinkedList<ActiveClient>();
//	private ServerLogger logger;

	private LinkedList<ClientHandler> clientList = new LinkedList<>(); // Till�gg Johan

	public Server(int inPort, ServerController inController) {
		this.controller = inController;
		controller.registerServer(this);
		controller.setUpLogger(logger);
		controller.setServerGUI();

		try {
			serverSocket = new ServerSocket(inPort);
			logger.log(Level.INFO, this.toString() + " running on port " + inPort);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString());
			e.printStackTrace();
		}

		Thread cl = new ClientListener();
		cl.start();
	}

//	public LinkedList<ActiveClient> getConnectedClients() {
//		return this.connectedClients;
//	}

	private class ClientListener extends Thread {

		public void run() {
			try {
				while (true) {
					socket = serverSocket.accept();
					Thread ch = new ClientHandler(socket);
					ch.start();
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.toString());
				try {
					socket.close();
					// controller.remove(socket); // Ers�ttas med user
				} catch (IOException e1) {
					logger.log(Level.SEVERE, e1.toString());
					e1.printStackTrace();
				}
			}
		}
	}

	private class ClientHandler extends Thread {
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private User user;
		
		public ClientHandler(Socket inSocket) {
			this.socket = inSocket;

			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
//				controller.newClient(new ClientConnection(oos, socket));
				userWriter = new UserWriter(oos);
				logger.log(Level.INFO, "New client connected: " + socket.toString());
			} catch (IOException e) {
				logger.log(Level.WARNING, e.toString());
				e.printStackTrace();
			}
			clientList.add(this); // Till�gg Johan
		}

		public void run() {
			try {
				Message message;
				Object obj;

				while (true) {
					obj = ois.readObject();
					
					if (obj instanceof Message) {
						message = (Message) obj;
						controller.newMessage(message, savedUsers);
					}
//					} else if (obj instanceof User) {
//						controller.addNewUser((User) obj);
//						logger.info("User " + obj.toString() + " added"); 
//						// skriv user?
//					}

					/*
					 * Logga in användare med loginobject
					 */
					else if (obj instanceof LogInObject) {
						LogInObject logInUser = (LogInObject) obj;
						LoginStatus status = new LoginStatus();

//						User haj = logInUser.getUser();

						if (savedUsers.logInUser(logInUser.getName(), logInUser.getPassword()) == 1) {
							logger.log(Level.INFO, logInUser.getName() + " logged in");
							status.setLoginSucessfull(1);
							status.setUser(savedUsers.getUser(logInUser.getName()));
							this.user = savedUsers.getUser(logInUser.getName());
//							connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
							
							new WriteMessage(status, oos); // För att skicka statusen av inloggningen till clienten
							writeUserList(status);							
//							new WriteMessage(connectedClients, oos); // För att uppdatera lista med användare
						} else {
							logger.log(Level.INFO, logInUser.getName() + " failed to log in");
						}
					}

					/*
					 * Skapa ny användare med User objekt
					 */
					else if (obj instanceof User) {
						User user = (User) obj;
						LoginStatus status = new LoginStatus();

						if (savedUsers.controlUser(user) == 0) {
							logger.log(Level.INFO, "New user added: " + user.getName());
							savedUsers.saveNewUser(user);
							status.setLoginSucessfull(1);
							status.setUser(user);
							this.user = user;
//							connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
							new WriteMessage(status, oos);
							writeUserList(status);		
//							new WriteMessage(connectedClients, oos);
						} else {
							System.out.println("Användare finns redan");
						}

					} else if (obj.equals("getActiveUserList")) {
						oos.writeObject(savedUsers.getUsers());
						oos.flush();
					}
					
					else {
						logger.log(Level.WARNING, obj.toString() + " could not be converted");
					}

				}
			} catch (IOException e) {
				logger.log(Level.WARNING, e.toString());
				clientList.remove(this);
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e1) {
					logger.log(Level.SEVERE, e1.toString());
					e1.printStackTrace();
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		public User getUser() {
			return user;
		}
		
		public ObjectOutputStream getOos() {
			return oos;
		}
		
		private void writeUserList(LoginStatus status) throws IOException {
			for(ClientHandler client : clientList) {
				if(!client.equals(this)) {
					client.oos.writeObject(status.getLoggedInUser());
					client.oos.flush();
				} 
			}
		}
	}

	public void sendMessage(Message inMessage, User user) {
		for(ClientHandler ch : clientList) {
			if(ch.getUser().equals(user)) {
				new WriteMessage(inMessage, ch.getOos());
			}
		}
	}

	private class WriteMessage extends Thread {
		private Object message;
		private ObjectOutputStream oos;

		public WriteMessage(Object message, ObjectOutputStream oos) {
			this.message = message;
			this.oos = oos;
			start();
		}

		public void run() {
			try {
				oos.writeObject(message);
				oos.flush();
				if (message instanceof Message) {
					Message m = (Message) message;
					logger.log(Level.INFO,
							m.getText() + " sent from " + m.getSender().getName() + " to " + m.getReceiver().getName());
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.toString());
				try {
					socket.close();
				} catch (IOException e1) {
					logger.log(Level.SEVERE, e1.toString());
					e1.printStackTrace();
				}
			}
		}
	}

	public void writeUsers(ArrayList<User> users) {
		userWriter.newUsers(users);
	}

	private class UserWriter {
		private ArrayList<User> users;
		private ObjectOutputStream userOos;

		public UserWriter(ObjectOutputStream oos) {
			this.userOos = oos;
		}

		public void newUsers(ArrayList<User> users) {
//			this.users = users;
			write();
		}

		public void write() {
			try {
				userOos.writeObject(users);
				userOos.flush();
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}