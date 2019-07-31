package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import message.Message;
import saveUsers.SavedUsers;
import user.User;

public class ServerController
{
	private final String logFile = "files/log.txt";
	private Server server;
	private LinkedList<ClientConnection> clients = new LinkedList<>();
	private ArrayList<User> connectedUsers = new ArrayList<>();
	private ServerHistory gui;
	
	public void registerServer(Server inServer)
	{
		this.server = inServer; 
	}

	public void newClient(ClientConnection cc)
	{
		clients.add(cc);
	}

	public LinkedList<ClientConnection> getClientList()
	{
		return clients;
	}

	public void newMessage(Message message, SavedUsers users) // Lagra SavedUsers m.m. i controller istället
	{
		for(User user : users.getUsers())
		{	
			if(message.getReceiver().equals(user)) // Kastar NullPointerException!
			{
				server.sendMessage(message, user);
			}
		}
	}

	public void addNewUser(User user)
	{
		connectedUsers.add(user);
		writeUsers();
	}
	
	public void writeUsers()
	{
		server.writeUsers(connectedUsers);
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkDate(String from, String to) {
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter logFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
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
