package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import client.ClientConnection;
import client.LogInObject;
import client.RequestList;
import message.Message;
import saveUsers.SavedUsers;
//import server.Server.WriteMessage;
import user.User;

public class Server {
	private ServerController controller;
	private ServerSocket serverSocket;
	private Socket socket;
	private UserWriter userWriter;
	private SavedUsers savedUsers = new SavedUsers("files/SavedUsers.dat");
	private LinkedList<ActiveClient> connectedClients = new LinkedList<ActiveClient>();
	private ServerLogger logger = new ServerLogger();

	public Server(int inPort, ServerController inController) {
		this.controller = inController;
		try {
			serverSocket = new ServerSocket(inPort);
<<<<<<< HEAD
		} catch (IOException e) {
			logger.severe(e.toString());
=======
			System.out.println("Server startad på port " + inPort);
		} catch (IOException e)
		{
>>>>>>> 26359d9d4384031ef69191661cdec5e9c18d579c
			e.printStackTrace();
		}
		controller.registerServer(this);
		new ClientListener(inPort);
		logger.info(this.toString() + " running on port " + inPort);
	}

	public LinkedList<ActiveClient> getConnectedClients() {
		return this.connectedClients;
	}

	private class ClientListener extends Thread {
		private int port;

		public ClientListener(int inPort) {
			this.port = inPort;
			start();
		}

		public void run() {
			try {
				while (true) {
					socket = serverSocket.accept();
					new ClientHandler(socket);
				}
			} catch (IOException e) {
				logger.severe(e.toString());
				try {
					socket.close();
					// controller.remove(socket); // Ers�ttas med user
				} catch (IOException e1) {
					logger.severe(e1.toString());
					e1.printStackTrace();
				}
			}
		}
	}

	private class ClientHandler extends Thread {
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public ClientHandler(Socket inSocket) {
			this.socket = inSocket;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				controller.newClient(new ClientConnection(oos, socket));
				userWriter = new UserWriter(oos);
				logger.info("Client added: oos=" + oos.toString());
			} catch (IOException e) {
				logger.warning(e.toString());
				e.printStackTrace();
			}

			start();
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
					} else if (obj instanceof User) {
						controller.addNewUser((User) obj);
						logger.info("User " + obj.toString() + " added"); 
						// skriv user?
					}

					else if (obj == null) {
						logger.warning("Object sent from " + this.toString() + " = null");
						System.out.println("objekt är null");
					}

					/*
					 * Logga in användare med loginobject
					 */
					else if (obj instanceof LogInObject) {
						LogInObject logInUser = (LogInObject) obj;
						LoginStatus status = new LoginStatus();
				

						if (savedUsers.logInUser(logInUser.getName(), logInUser.getPassword()) == 1) {
							logger.info(logInUser.getName() + " logged in");
							System.out.println("inloggning lyckad");
							status.setLoginSucessfull(1);
							status.setUser(savedUsers.getUser());
							controller.newClient(new ClientConnection(oos, socket, savedUsers.getUser()));
							
							new WriteMessage(status, oos); 
							
						} else {
							logger.info(logInUser.getName() + " failed to log in");
							System.out.println("inloggning ej godkänd");
						}

					}

					/*
					 * Skapa ny användare med User objekt
					 */
					else if (obj instanceof User) {		// MARKER: Also checked at line 101 
						User user = (User) obj;
						LoginStatus status = new LoginStatus();

						if (savedUsers.controlUser(user) == 0) {
							logger.info("New user added: " + user.getName());
							System.out.println("Ny användare skapad!");
							savedUsers.saveNewUser(user);
							status.setLoginSucessfull(1);
							status.setUser(user);
							controller.newClient(new ClientConnection(oos, socket, status.getLoggedInUser()));
							new WriteMessage(status, oos);
						} else {
							System.out.println("Användare finns redan");
						}

					}
					
					else if(obj instanceof RequestList) {
						RequestList sendList = (RequestList) obj;
						
						System.out.println("Nu ska lista skickas till alla klienter?");
						if(sendList.getStatus() == 1) {
//							new WriteMessage(connectedClients, oos);
//							controller.newObject(sendList);
							
//							Om Detta körs fastnar koden och det går inte att skicka meddelanden?
//							UserListHolder tempList = new UserListHolder();
//							tempList.setList(controller.getClientList());
//							controller.newObject(tempList);
						}
						
					}

					else {
						logger.warning(obj.toString() + " could not be converted");
						System.out.println("Objekt kan inte typkonverteras");
					}

				}
			} catch (IOException | ClassNotFoundException e) {
				logger.severe(e.toString());
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e1) {
					logger.severe(e1.toString());
					e1.printStackTrace();
				}
			}
		}
	}

	public void sendMessage(Object inMessage, ObjectOutputStream inOos) {
		new WriteMessage(inMessage, inOos);

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
				if(message instanceof Message) {
					Message m = (Message)message;
					logger.info(m.getText() + " sent from " + m.getSender().getName() +
							" to " + m.getReceiver().getName());
				}
			} catch (IOException e) {
				logger.severe(e.toString());
				try {
					socket.close();
				} catch (IOException e1) {
					logger.severe(e1.toString());
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
			this.users = users;
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