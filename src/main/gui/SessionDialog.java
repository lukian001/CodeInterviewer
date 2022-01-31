package main.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import main.customs.CustomCalendar;
import main.customs.CustomTable;
import main.customs.InputField;
import main.enums.ControlDimensions;
import main.exceptions.BlankFieldException;
import main.exceptions.InvalidDateException;
import main.exceptions.InvalidUserException;
import main.exceptions.InviteOnePersonException;
import main.exceptions.UserAlreadyExistsException;
import main.handlers.AddSessionHandler;
import main.handlers.AddUserHandler;
import main.model.User;
import main.parents.Dialog;

public class SessionDialog extends Dialog {

	private User user;
	private MainDialog dialog;
	private Button startTime;
	private List<User> invitedUsers = new ArrayList<>();

	public SessionDialog(String title, User user, MainDialog dialog) {
		super(title, false);
		this.user = user;
		this.dialog = dialog;
	}

	@Override
	public void createDialog() {
		shell.setLayout(new GridLayout(1, false));
		InputField name = new InputField(shell, SWT.BORDER, ControlDimensions.INPUT_FIELD_WIDTH,
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		name.setText("Session name:");

		Composite durationComposite = new Composite(shell, SWT.NONE);
		durationComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		durationComposite.setLayout(new FormLayout());

		startTime = new Button(durationComposite, SWT.NONE);
		startTime.setLayoutData(getFromData(0, 100, 0, 60));
		startTime.setText("Select START TIME");

		Combo duration = new Combo(durationComposite, SWT.NONE);
		duration.setLayoutData(getFromData(5, 95, 60, 100));
		duration.add("30");
		duration.add("45");
		duration.add("60");
		duration.add("90");

		Composite addUserComposite = new Composite(shell, SWT.NONE);
		addUserComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addUserComposite.setLayout(new FormLayout());

		Text userField = new Text(addUserComposite, SWT.BORDER);
		userField.setLayoutData(getFromData(5, 95, 0, 70));
		userField.setMessage("Invite user's username");

		Button addUser = new Button(addUserComposite, SWT.NONE);
		addUser.setLayoutData(getFromData(0, 100, 70, 100));
		addUser.setText("ADD");

		Label errorLabel = new Label(shell, SWT.NONE);
		errorLabel.setText("-".repeat(62));
		errorLabel.setVisible(false);

		CustomTable table = new CustomTable(shell, false, 400, this);
		table.setColumns(User.getColumns());
		table.fill(invitedUsers);

		Button addSession = new Button(shell, SWT.NONE);
		addSession.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addSession.setText("Add Session");

		startTime.addListener(SWT.MouseUp, e -> {
			new CustomCalendar("Calendar", this).run();
		});
		addUser.addListener(SWT.MouseUp, e -> {
			try {
				User userN = new AddUserHandler(user.getUserName(), userField.getText(), invitedUsers).process();

				if (userN != null) {
					invitedUsers.add(userN);
					table.redraw(invitedUsers);
				}
			} catch (BlankFieldException | UserAlreadyExistsException | InvalidUserException e1) {
				errorLabel.setText(e1.getMessage());
				errorLabel.setVisible(true);
			}
		});
		addSession.addListener(SWT.MouseUp, e -> {
			try {
				new AddSessionHandler(user, name.getText(), startTime.getText(), duration.getText(), invitedUsers)
						.process();

				this.dispose();
				dialog.reinitialize();
			} catch (BlankFieldException | InvalidDateException | InviteOnePersonException e1) {
				e1.printStackTrace();
			}
		});
		userField.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				try {
					User userN = new AddUserHandler(user.getUserName(), userField.getText(), invitedUsers).process();

					if (userN != null) {
						invitedUsers.add(userN);
						table.redraw(invitedUsers);
					}
				} catch (BlankFieldException | UserAlreadyExistsException | InvalidUserException e1) {
					errorLabel.setText(e1.getMessage());
					errorLabel.setVisible(true);
				}
			}
		});
	}

	private FormData getFromData(int top, int bottom, int left, int right) {
		FormData form = new FormData();

		form.top = new FormAttachment(top);
		form.bottom = new FormAttachment(bottom);
		form.left = new FormAttachment(left);
		form.right = new FormAttachment(right);

		return form;
	}

	public Button getTimeButton() {
		return startTime;
	}
}
