package main.exceptions;

public class InvalidUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1096939434615232443L;

	public InvalidUserException(String message) {
		super(message);
	}

}
