package main.handlers;

import java.rmi.RemoteException;

import main.Utils;
import main.exceptions.BlankFieldException;
import main.exceptions.InvalidPasswordsException;
import main.exceptions.NotEmailException;
import main.exceptions.UserAlreadyExistsException;
import main.model.User;
import main.server.ApplicationSession;
import main.server.ServerCalls;

public class RegisterHandler {

	private String username;
	private String email;
	private String password;
	private String confirmPassword;

	public RegisterHandler(String username, String email, String password, String confirmPassword) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public void process()
			throws BlankFieldException, InvalidPasswordsException, NotEmailException, UserAlreadyExistsException {
		if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
			throw new BlankFieldException("Please fill all the fields!");
		} else if (!password.equals(confirmPassword)) {
			throw new InvalidPasswordsException("The passwords does not match!");
		} else if (!Utils.checkIfEmail(email)) {
			throw new NotEmailException("Please use a valid email!");
		}

		try {
			ServerCalls serverCalls = ApplicationSession.getInstance().getServer();
			if (serverCalls.getUserByUsername(username) != null || serverCalls.getUserByEmail(email) != null) {
				throw new UserAlreadyExistsException("This username/email already exists!");
			}

			User createdUser = new User(username, password, email);
			serverCalls.addUser(createdUser);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
