package main.exceptions;

public class UserAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4168153344275595664L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
