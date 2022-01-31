package main.gui;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import main.customs.CustomTable;
import main.customs.InputField;
import main.enums.ControlDimensions;
import main.gui.service.RefillTableService;
import main.model.Session;
import main.model.User;
import main.parents.Dialog;
import main.server.ApplicationSession;

public class MainDialog extends Dialog {

	private User user;
	private CustomTable table;
	private InputField searchField;
	private RefillTableService srvc;

	public MainDialog(String dialogTitle, String username) {
		super(dialogTitle, true);

		try {
			user = ApplicationSession.getInstance().getServer().getUserByUsername(username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.srvc = new RefillTableService(this);

		this.shell.addListener(SWT.Close, e -> {
			srvc.stopService();
			this.getShell().getDisplay().dispose();
		});
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new GridLayout());

		searchField = new InputField(shell, SWT.BORDER, ControlDimensions.INPUT_FIELD_WIDTH,
				ControlDimensions.INPUT_FIELD_HEIGHT, false);
		Composite buttonsComposite = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		buttonsComposite.setLayout(new FillLayout());
		Button profileButton = new Button(buttonsComposite, SWT.NONE);
		profileButton.setText("PROFILE");
		Button sessionButton = new Button(buttonsComposite, SWT.NONE);
		sessionButton.setText("ADD SESSION");

		table = new CustomTable(shell, true, 500, this);
		table.setColumns(Session.getColumns());
		table.fill(user.getSessions());

		profileButton.addListener(SWT.MouseUp, e -> {
			new ProfileDialog("Profile - " + user.getUserName(), user, this).run();
		});
		sessionButton.addListener(SWT.MouseUp, e -> {
			new SessionDialog("Add Session", user, this).run();
		});

		srvc.start();

		searchField.getField().addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (!searchField.getText().isBlank()) {
					srvc.pauseService();
				} else {
					srvc.startService();
				}

				List<Session> ses = user.getSessions().stream()
						.filter(se -> se.getName().contains(searchField.getText())).collect(Collectors.toList());
				table.redraw(ses);
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

	}

	public void setUser(User user) {
		this.user = user;
	}

	public void reinitialize() {
		try {
			user = ApplicationSession.getInstance().getServer().getUserByUsername(user.getUserName());
			searchField.getField().setText("");
			table.redraw(user.getSessions());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}

	public CustomTable getTable() {
		return table;
	}

	public Shell getShell() {
		return this.shell;
	}

	public String getUsername() {
		return user.getUserName();
	}
}
