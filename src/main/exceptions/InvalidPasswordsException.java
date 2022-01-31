package main.exceptions;

public class InvalidPasswordsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1219032687841672414L;

	public InvalidPasswordsException(String message) {
		super(message);
	}

}
