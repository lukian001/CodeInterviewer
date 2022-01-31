package main.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

import main.customs.InputField;
import main.enums.ControlDimensions;
import main.exceptions.BlankFieldException;
import main.exceptions.InvalidPasswordsException;
import main.exceptions.NotEmailException;
import main.exceptions.UserAlreadyExistsException;
import main.handlers.RegisterHandler;
import main.parents.Dialog;

public class RegisterDialog extends Dialog {
	private InputField usernameField;
	private InputField emailField;
	private InputField passwordField;
	private InputField confirmPasswordField;

	private Label errorLabel;

	public RegisterDialog(String title) {
		super(title, false);
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new GridLayout(1, false));
		this.shell.addListener(SWT.Close, e -> {
			new LogInDialog("Code Interviewer - LOG IN").run();
			this.dispose();
		});

		usernameField = new InputField(shell, SWT.BORDER, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		usernameField.setText("Username: ");

		emailField = new InputField(shell, SWT.BORDER, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		emailField.setText("Email: ");

		passwordField = new InputField(shell, SWT.BORDER | SWT.PASSWORD, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		passwordField.setText("Password: ");

		confirmPasswordField = new InputField(shell, SWT.BORDER | SWT.PASSWORD, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		confirmPasswordField.setText("Confirm Password: ");

		errorLabel = new Label(shell, SWT.NONE);
		errorLabel.setText("-".repeat(62));
		errorLabel.setVisible(false);

		Button register = new Button(shell, SWT.NONE);
		register.setText("REGISTER");

		register.addListener(SWT.MouseUp, e -> {
			registerProcess();
		});
	}

	private void registerProcess() {
		try {
			new RegisterHandler(usernameField.getText(), emailField.getText(), passwordField.getText(),
					confirmPasswordField.getText()).process();

			new MainDialog("Code Interviewer", usernameField.getText()).run();
			this.shell.dispose();
		} catch (BlankFieldException | InvalidPasswordsException | NotEmailException | UserAlreadyExistsException e) {
			errorLabel.setText(e.getMessage());
			errorLabel.setVisible(true);
		}
	}

}
