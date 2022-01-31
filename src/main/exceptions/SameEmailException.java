package main.exceptions;

public class SameEmailException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2853539873905563983L;

	public SameEmailException(String message) {
		super(message);
	}
}
