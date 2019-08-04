package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import login.LoginObject;
import login.LoginStatus;
import message.Message;
//import server.Server.WriteMessage;
import user.User;

public class Server {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private ServerController controller;
	private ServerSocket serverSocket;
	private Socket socket;
//	private UserWriter userWriter;
	private LinkedList<ClientHandler> clientList = new LinkedList<>();

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
		private LoginStatus status;
		
		public ClientHandler(Socket inSocket) {
			this.socket = inSocket;
			
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
//				controller.newClient(new ClientConnection(oos, socket));
//				userWriter = new UserWriter(oos);
				logger.log(Level.INFO, "New client connected: " + socket.toString());
			} catch (IOException e) {
				logger.log(Level.WARNING, e.toString());
				e.printStackTrace();
			}
			clientList.add(this);
		}

		public void run() {
			try {
				Message message;
				Object obj;

				while (true) {
					obj = ois.readObject();
					
					if (obj instanceof Message) {
						message = (Message) obj;
						controller.newMessage(message);
					}
//					} else if (obj instanceof User) {
//						controller.addNewUser((User) obj);
//						logger.info("User " + obj.toString() + " added"); 
//						// skriv user?
//					}

					/*
					 * Logga in användare med loginobject
					 */
					else if (obj instanceof LoginObject) {
						LoginObject login = (LoginObject)obj;
						status = controller.validateLogin(login);
						if(status.getLoginStatus() == 1) {
							user = status.getLoggedInUser();
							logger.log(Level.INFO, user.getName() + " logged in");
							controller.addUser(oos, user);
							oos.writeObject(status);
							oos.writeObject(controller.getUserList());
							oos.flush();
//							writeUserList(status);
						} else {
							logger.log(Level.INFO, user.getName() + " failed to log in");
						}
					}

					/*
					 * Skapa ny användare med User objekt
					 */
					else if (obj instanceof User) {
						controller.createNewUser(user);
						

					} else if (obj instanceof LoginStatus) {
						LoginStatus status = (LoginStatus)obj;
						writeUserList(status);
					
					
					} else {
						logger.log(Level.WARNING, obj.toString() + " could not be converted");
					}

				}
			} catch (IOException e) {
				clientList.remove(this);
				if(user != null) {
					logger.log(Level.INFO, user + " logged out");
//					try {
//						for(ClientHandler client : clientList) {
//							client.getOos().writeObject(getActiveUsers());
//							client.getOos().flush();
//						}
//						
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					
				}
				try {
					if (socket != null) {
						socket.close();
						logger.log(Level.INFO, socket.toString() + " closed");
					}
				} catch (IOException e1) {
					logger.log(Level.SEVERE, e1.toString());
					e1.printStackTrace();
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		public ObjectOutputStream getOos() {
			return oos;
		}
		
		public User getUser() {
			return user;
		}
		
//		public LinkedList<User> getActiveUsers(){
//			LinkedList<User> users = new LinkedList<>();
//			for (ActiveUser user : activeUsers) {
//				if(!user.getUser().getName().equals(this.user.getName()))
//					users.add(user.getUser());
//			}
//			return users;
//		}
		
		private void writeUserList(LoginStatus status) throws IOException {
			for(ClientHandler client : clientList) {
				if(!client.equals(this)) {
					client.oos.writeObject(status.getLoggedInUser());
					client.oos.flush();
				} 
			}
		}
	}

//	public void sendMessage(Message inMessage, User user, ) {
//		for(ClientHandler ch : clientList) {
//			if(ch.getUser().equals(user)) {
//				System.out.println("Server: " + inMessage + ", " + ch.getUser() + ", " +ch.getOos());
//				new WriteMessage(inMessage, ch.getOos());
//			}
//		}
//	}
//
//	private class WriteMessage extends Thread {
//		private Object message;
//		private ObjectOutputStream oos;
//
//		public WriteMessage(Object message, ObjectOutputStream oos) {
//			this.message = message;
//			this.oos = oos;
//			start();
//		}
//
//		public void run() {
//			try {
//				oos.writeObject(message);
//				oos.flush();
//				if (message instanceof Message) {
//					Message m = (Message) message;
//					logger.log(Level.INFO,
//							m.getText() + " sent from " + m.getSender().getName() + " to " + m.getReceiver().getName());
//				}
//			} catch (IOException e) {
//				logger.log(Level.SEVERE, e.toString());
//				try {
//					socket.close();
//				} catch (IOException e1) {
//					logger.log(Level.SEVERE, e1.toString());
//					e1.printStackTrace();
//				}
//			}
//		}
//	}

//	public void writeUsers(ArrayList<User> users) {
//		userWriter.newUsers(users);
//	}
//
//	private class UserWriter {
//		private ArrayList<User> users;
//		private ObjectOutputStream userOos;
//
//		public UserWriter(ObjectOutputStream oos) {
//			this.userOos = oos;
//		}
//
//		public void newUsers(ArrayList<User> users) {
////			this.users = users;
//			write();
//		}
//
//		public void write() {
//			try {
//				userOos.writeObject(users);
//				userOos.flush();
//			} catch (IOException e) {
//				try {
//					socket.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//	}
}