package main.handlers;

import java.rmi.RemoteException;
import java.util.List;

import main.customs.InputField;
import main.exceptions.BlankFieldException;
import main.exceptions.InvalidPasswordException;
import main.exceptions.InvalidPasswordsException;
import main.model.User;
import main.server.ApplicationSession;

public class ChangePasswordHandler {

	private User user;
	private List<InputField> list;

	public ChangePasswordHandler(User user, List<InputField> list) {
		this.user = user;
		this.list = list;
	}

	public void process() throws InvalidPasswordException, InvalidPasswordsException, BlankFieldException {
		for (InputField field : list) {
			if (field.getText().isBlank()) {
				throw new BlankFieldException("Please fill all the fields!");
			}
		}
		if (!user.getPassword().equals(list.get(0).getText())) {
			throw new InvalidPasswordException("The current password is not correct!");
		} else if (!list.get(1).getText().equals(list.get(2).getText())) {
			throw new InvalidPasswordsException("The passwords does not match!");
		}

		try {
			ApplicationSession.getInstance().getServer().updateUserPassword(user, list.get(1).getText());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
