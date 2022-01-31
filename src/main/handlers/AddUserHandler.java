package main.handlers;

import java.rmi.RemoteException;
import java.util.List;

import main.exceptions.BlankFieldException;
import main.exceptions.InvalidUserException;
import main.exceptions.UserAlreadyExistsException;
import main.model.User;
import main.server.ApplicationSession;

public class AddUserHandler {

	private String username;
	private List<User> invitedUsers;
	private String currentUser;

	public AddUserHandler(String currentUser, String username, List<User> invitedUsers) {
		this.username = username;
		this.invitedUsers = invitedUsers;
		this.currentUser = currentUser;
	}

	public User process() throws BlankFieldException, UserAlreadyExistsException, InvalidUserException {
		if (username.isBlank()) {
			throw new BlankFieldException("Please fill the field!");
		} else if (invitedUsers.stream().filter(u -> u.getUserName().equals(username)).findFirst()
				.orElse(null) != null) {
			throw new UserAlreadyExistsException("This user is already invited!");
		} else if (currentUser.equals(username)) {
			throw new InvalidUserException("You can't invite yourself!");
		}

		User user = null;
		try {
			user = ApplicationSession.getInstance().getServer().getUserByUsername(username);

			if (user == null) {
				throw new InvalidUserException("This user doens't exists!");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return user;
	}

}
