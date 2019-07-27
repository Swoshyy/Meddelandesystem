package client;

import java.io.Serializable;

/**
 * Efter logInStatus skickats tillbaka vid inloggning och är 1 skapas ett RequestList 
 * objekt till servern som sedan skcikar tillbaka listan med alla uppkopplade användare
 * @author sethoberg
 *
 */
public class RequestList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID =  -464055306876372645L;
	private int logInSucessfull = 0;

	public RequestList(int status) {
		this.logInSucessfull = status;
	}
	
	public int getStatus() {
		return logInSucessfull;
	}
	
}
