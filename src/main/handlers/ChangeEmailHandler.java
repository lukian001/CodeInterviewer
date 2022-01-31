package main.handlers;

import java.rmi.RemoteException;

import main.Utils;
import main.exceptions.NotEmailException;
import main.exceptions.SameEmailException;
import main.exceptions.UserAlreadyExistsException;
import main.model.User;
import main.server.ApplicationSession;

public class ChangeEmailHandler {

	private User user;
	private String email;

	public ChangeEmailHandler(User user, String email) {
		this.user = user;
		this.email = email;
	}

	public void process() throws UserAlreadyExistsException, SameEmailException, NotEmailException {
		if (email.equals(user.getEmail())) {
			throw new SameEmailException("You cant use the same email!");
		} else if (!Utils.checkIfEmail(email)) {
			throw new NotEmailException("Please use a valid email!");
		}
		try {
			User usr = ApplicationSession.getInstance().getServer().getUserByEmail(email);
			if (usr == null) {
				ApplicationSession.getInstance().getServer().updateUserEmail(user, email);
			} else {
				throw new UserAlreadyExistsException("This email already exists!");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
