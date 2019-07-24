package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import GUI.ServerHistory;
/**
 * 
 * @author Johan Steen Wingren
 *
 */
public class ServerLogger {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final String logFile = "files/log.txt";
//	private static FileHandler fileHandler;
	private DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private DateTimeFormatter logFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
	private ServerHistory gui;
	
	public ServerLogger() {
		try {
			FileHandler fileHandler = new FileHandler(logFile, true);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		}catch (IOException e) {
			e.printStackTrace();
		}		
		logger.setLevel(Level.INFO);		
		gui = new ServerHistory(this);
	}
	
	public void info(String message) {
		logger.log(Level.INFO, message);
	}
	
	public void warning(String message) {
		logger.log(Level.WARNING, message);
	}
	
	public void severe(String message) {
		logger.log(Level.SEVERE, message);
	}
	
	public void checkDate(String from, String to) {
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
			}catch(DateTimeParseException e) {	
				gui.showLog("ERROR: Date must be written yyyy/mm/dd");
			}
			try {
				String currentLine = logReader.readLine();
				while (currentLine != null) {
					parts = currentLine.split(" ");
					if(!parts[0].substring(parts[0].length()-1).equals(":")) {
						dateStr = parts[0] + " " + parts[1] + " " + parts[2];
						LocalDate logDate = LocalDate.parse(dateStr, logFormat);
						if((logDate.isAfter(fromDate) || logDate.isEqual(fromDate)) 
								&& (logDate.isBefore(toDate) || logDate.isEqual(toDate))) {
							gui.showLog(currentLine);
							gui.showLog(logReader.readLine());
							entryFound = true;
						}
					}
					currentLine = logReader.readLine();
				}
				if(!entryFound) {
					gui.showLog("No entries for the selected period");
				}
				logReader.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
