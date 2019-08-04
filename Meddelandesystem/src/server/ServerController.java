package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.SwingUtilities;

import GUI.ServerHistory;
import login.LoginObject;
import login.LoginStatus;
import message.Message;
import saveUsers.SavedUsers;
import user.User;

public class ServerController
{
	private final String logFile = "files/log.txt";
	private Server server;
	private ServerHistory gui; 
	private LinkedList<ActiveUser> activeUsers = new LinkedList<>();
	private SavedUsers savedUsers = new SavedUsers("files/SavedUsers.dat");
	
	public void registerServer(Server inServer)
	{
		this.server = inServer; 
	}
	
	public LoginStatus validateLogin(LoginObject login) {
		LoginStatus status = new LoginStatus();
		String name = login.getName();
		String password = login.getPassword();
		
		if (savedUsers.logInUser(name, password) == 1) {
			status.setLoginSucessfull(1);
			status.setUser(savedUsers.getUser(name));
//			activeUsers.add(new ActiveUser(oos, this.user));
//			connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
			
//			new WriteMessage(status, oos); // För att skicka statusen av inloggningen till clienten
//			writeUserList(status);							
//			new WriteMessage(connectedClients, oos); // För att uppdatera lista med användare�

		}
		return status;
	}
	
	public void addUser(ObjectOutputStream oos, User user) {
		activeUsers.add(new ActiveUser(oos, user));
		for(ActiveUser active : activeUsers) {
			System.out.println(active.getUser().getName());
		}
		
	}
	
	public LinkedList<User> getUserList() {
		LinkedList<User> userList = new LinkedList<>();
		for(ActiveUser activeUser : activeUsers) {
			userList.add(activeUser.getUser());
		}
		return userList;
	}
	
	public void createNewUser(Object obj) {
		User user = (User) obj;
		LoginStatus status = new LoginStatus();
//		status = new LoginStatus();
		
		if (savedUsers.controlUser(user) == 0) {
//			logger.log(Level.INFO, "New user added: " + user.getName());
			savedUsers.saveNewUser(user);
			status.setLoginSucessfull(1);
			status.setUser(user);
//			this.user = user;
//			activeUsers.add(new ActiveUser(oos, this.user));
//			connectedClients.add(new ActiveClient(oos, status.getLoggedInUser()));
//			oos.writeObject(status);
//			oos.flush();
//			new WriteMessage(status, oos);
//			writeUserList(status);		
//			new WriteMessage(connectedClients, oos);
		} else {
			System.out.println("Användare finns redan");
		}
	}
	
//	public void newClient(ClientConnection cc)
//	{
//		clients.add(cc);
//	}

//	public LinkedList<ClientConnection> getClientList()
//	{
//		return clients;
//	}

	public void newMessage(Message message) throws IOException {
		ObjectOutputStream oos;
		String receiver = message.getReceiver().getName();
		String sender = message.getSender().getName();
		for(ActiveUser user : activeUsers)
		{	
			if(receiver.equals(user.getUser().getName()) 
					|| sender.equals(user.getUser().getName())) {
				oos = user.getOuputStream();
				try {
					oos.writeObject(message);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

//	public void addNewUser(User user)
//	{
//		connectedUsers.add(user);
//		writeUsers();
//	}
	
//	public void writeUsers()
//	{
//		server.writeUsers(connectedUsers);
//	}

	public void setServerGUI() {
		SwingUtilities.invokeLater(new  Runnable() {
			public void run() {
				gui = new ServerHistory(ServerController.this);
			}
		});
	}
	
	public void setUpLogger(Logger logger) {
		try {
			FileHandler fileHandler = new FileHandler(logFile, true);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			logger.setLevel(Level.INFO);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkDate(String from, String to) {
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter logFormat = DateTimeFormatter.ofPattern("MMM d, yyyy");
		try {
			BufferedReader logReader = new BufferedReader(new FileReader(logFile));
			String[] parts;
			String dateStr = "";
			LocalDate fromDate = null;
			LocalDate toDate = null;
			boolean entryFound = false;
			try {
				fromDate = LocalDate.parse(from, inputFormat);
				toDate = LocalDate.parse(to, inputFormat);
			} catch (DateTimeParseException e) {
				gui.showLog("ERROR: Date must be written yyyy/mm/dd");
			}
			try {
				String currentLine = logReader.readLine();
				while (currentLine != null) {
					parts = currentLine.split(" ");
					if (!parts[0].substring(parts[0].length() - 1).equals(":")) {
						dateStr = parts[0] + " " + parts[1] + " " + parts[2];
						LocalDate logDate = LocalDate.parse(dateStr, logFormat);
						if ((logDate.isAfter(fromDate) || logDate.isEqual(fromDate))
								&& (logDate.isBefore(toDate) || logDate.isEqual(toDate))) {
							gui.showLog(currentLine);
							gui.showLog(logReader.readLine());
							entryFound = true;
						}
					}
					currentLine = logReader.readLine();
				}
				if (!entryFound) {
					gui.showLog("No entries for the selected period");
				}
				logReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
