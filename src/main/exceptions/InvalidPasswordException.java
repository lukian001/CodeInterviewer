package main.exceptions;

public class InvalidPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -878770469114670334L;

	public InvalidPasswordException(String message) {
		super(message);
	}

}
