package main.gui;

import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

import main.exceptions.BlankFieldException;
import main.exceptions.InvalidPasswordException;
import main.exceptions.InvalidPasswordsException;
import main.exceptions.NotEmailException;
import main.exceptions.SameEmailException;
import main.exceptions.UserAlreadyExistsException;
import main.handlers.ChangeEmailHandler;
import main.handlers.ChangePasswordHandler;
import main.model.User;
import main.parents.Dialog;
import main.server.ApplicationSession;

public class ProfileDialog extends Dialog {

	private User user;
	private MainDialog startedIn;

	public ProfileDialog(String title, User user, MainDialog mainDialog) {
		super(title, false);
		this.user = user;
		this.startedIn = mainDialog;
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new GridLayout(2, false));

		Label username = new Label(shell, SWT.NONE);
		Label usernameData = new Label(shell, SWT.NONE);
		Label email = new Label(shell, SWT.NONE);
		Label emailData = new Label(shell, SWT.NONE);
		Label password = new Label(shell, SWT.NONE);
		Label passwordData = new Label(shell, SWT.NONE);

		username.setText("Username: ");
		email.setText("Email: ");
		password.setText("Password: ");
		usernameData.setText(user.getUserName());
		emailData.setText(user.getEmail());
		passwordData.setText(user.getPassword());

		Button changeEmailButton = new Button(shell, SWT.NONE);
		Button changePasswordButton = new Button(shell, SWT.NONE);

		changeEmailButton.setText("Change Email");
		changePasswordButton.setText("Change Password");

		changeEmailButton.addListener(SWT.MouseUp, e -> {
			ModularDialog dialog = new ModularDialog("Change Email", //
					new String[] { "Current email:", //
							"New email:" }, //
					new int[] { SWT.BORDER, SWT.BORDER }, //
					new String[] { "Proceed" });
			dialog.run();

			dialog.fill(new String[] { user.getEmail(), "" });
			dialog.setDisabled(0);

			dialog.getButtons().get(0).addListener(SWT.MouseUp, e1 -> {
				try {
					new ChangeEmailHandler(user, dialog.getFields().get(1).getText()).process();

					user = ApplicationSession.getInstance().getServer().getUserByUsername(user.getUserName());
					startedIn.setUser(user);
					dialog.dispose();
				} catch (UserAlreadyExistsException | SameEmailException | NotEmailException e2) {
					dialog.setErrorMessage(e2.getMessage());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
			});
		});
		changePasswordButton.addListener(SWT.MouseUp, e ->

		{
			ModularDialog dialog = new ModularDialog("Change Email", //
					new String[] { "Current password:", //
							"New password:", //
							"Confirm new password:" }, //
					new int[] { SWT.BORDER | SWT.PASSWORD, //
							SWT.BORDER | SWT.PASSWORD, //
							SWT.BORDER | SWT.PASSWORD }, //
					new String[] { "Proceed" });

			dialog.run();

			dialog.getButtons().get(0).addListener(SWT.MouseUp, e1 -> {
				try {
					new ChangePasswordHandler(user, dialog.getFields()).process();

					user = ApplicationSession.getInstance().getServer().getUserByUsername(user.getUserName());
					startedIn.setUser(user);
					dialog.dispose();
				} catch (InvalidPasswordException | InvalidPasswordsException | BlankFieldException e2) {
					dialog.setErrorMessage(e2.getMessage());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
			});
		});

	}

}
