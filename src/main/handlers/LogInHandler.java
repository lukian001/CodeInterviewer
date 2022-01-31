package main.handlers;

import java.rmi.RemoteException;

import main.exceptions.BlankFieldException;
import main.exceptions.InvalidUserException;
import main.server.ApplicationSession;
import main.server.ServerCalls;

public class LogInHandler {

	private String username;
	private String password;

	public LogInHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void process() throws BlankFieldException, InvalidUserException {
		if (username.isBlank() || password.isBlank()) {
			throw new BlankFieldException("Please fill all the fields!");
		}
		try {
			ServerCalls serverCalls = ApplicationSession.getInstance().getServer();
			if (serverCalls.checkLoginCredentials(username, password) == null) {
				throw new InvalidUserException("This username/password combination does not exist!");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
