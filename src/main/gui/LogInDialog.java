package main.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import main.customs.InputField;
import main.enums.ControlDimensions;
import main.exceptions.BlankFieldException;
import main.exceptions.InvalidUserException;
import main.handlers.LogInHandler;
import main.parents.Dialog;

public class LogInDialog extends Dialog {
	private InputField usernameField;
	private InputField passwordField;

	private Label errorLabel;

	public LogInDialog(String dialogTitle) {
		super(dialogTitle, true);
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new GridLayout(1, false));

		usernameField = new InputField(shell, SWT.BORDER, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		usernameField.setText("Username:");

		passwordField = new InputField(shell, SWT.BORDER | SWT.PASSWORD, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		passwordField.setText("Password:");

		errorLabel = new Label(shell, SWT.NONE);
		errorLabel.setText("-".repeat(62));
		errorLabel.setVisible(false);

		Composite buttonComposite = new Composite(shell, SWT.NONE);
		buttonComposite.setLayout(new RowLayout());

		Button logInButton = new Button(buttonComposite, SWT.NONE);
		logInButton.setText("LOG IN");

		Button registerButton = new Button(buttonComposite, SWT.NONE);
		registerButton.setText("REGISTER");

		usernameField.getField().addTraverseListener(getEnterListener());
		passwordField.getField().addTraverseListener(getEnterListener());

		logInButton.addListener(SWT.MouseUp, e -> {
			logInProcess();
		});

		registerButton.addListener(SWT.MouseUp, e -> {
			this.shell.dispose();
			new RegisterDialog("Code Interviewer - REGISTER").run();
		});
	}

	private void logInProcess() {
		try {
			new LogInHandler(usernameField.getText(), passwordField.getText()).process();

			new MainDialog("Code Interviewer", usernameField.getText()).run();
			this.shell.dispose();
		} catch (BlankFieldException | InvalidUserException e) {
			errorLabel.setText(e.getMessage());
			errorLabel.setVisible(true);
		}
	}

	private TraverseListener getEnterListener() {
		return new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					logInProcess();
				}
			}
		};
	}
}
