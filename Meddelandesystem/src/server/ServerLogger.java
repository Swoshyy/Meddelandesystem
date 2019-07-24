package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	private static FileHandler fileHandler;
	private BufferedReader logReader;
	private DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private DateTimeFormatter logFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
	private ServerHistory gui;
	
	public ServerLogger() {
		String logFile = "files/log.txt";
		try {
			fileHandler = new FileHandler(logFile, true);
		}catch (IOException e) {
			e.printStackTrace();
		}
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.setLevel(Level.INFO);
		try {
			logReader = new BufferedReader(new FileReader(logFile));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		gui = new ServerHistory(this);
	}
	
	public void log(String message) {
		logger.log(Level.INFO, message);
	}
	
	public void checkDate(String from, String to) {
		String[] parts;
		String dateStr = "";
		LocalDate fromDate = LocalDate.parse(from, inputFormat);
		LocalDate toDate = LocalDate.parse(to, inputFormat);
		try {
			String currentLine = logReader.readLine();
			while (currentLine != null) {
				parts = currentLine.split(" ");
				if(!parts[0].equals("INFO:")) {
					dateStr = parts[0] + " " + parts[1] + " " + parts[2];
					LocalDate logDate = LocalDate.parse(dateStr, logFormat);
					if((logDate.isAfter(fromDate) || logDate.isEqual(fromDate)) 
							&& (logDate.isBefore(toDate) || logDate.isEqual(toDate))) {
						gui.showLog(currentLine);
						gui.showLog(logReader.readLine());
					} else {
						gui.showLog("No entries for the selected period");
					}
				}
				currentLine = logReader.readLine();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void showLog(String message){
		String currentLine = "";
		try {
			currentLine = logReader.readLine();
			while(currentLine != null) {
				gui.showLog(currentLine);
				currentLine = logReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
