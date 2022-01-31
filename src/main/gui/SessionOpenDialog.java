package main.gui;

import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import main.gui.service.CodeService;
import main.handlers.RunCodehandler;
import main.model.Session;
import main.model.User;
import main.parents.Dialog;
import main.server.ApplicationSession;

public class SessionOpenDialog extends Dialog {
	private Session session;
	private CodeService codeService;
	private String currentUsername;

	private Label currentEditor;
	private Text codeText;
	private Combo invitedCombo;

	public static final int WIDTH = 500;
	public static final int CODE_HEIGHT = 600;
	public static final int RESULT_HEIGHT = 150;

	public SessionOpenDialog(TableItem tableItem, String currentUsername) {
		super("Session", false);

		try {
			this.session = ApplicationSession.getInstance().getServer()
					.getSessionText(Long.parseLong(tableItem.getText(0)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.currentUsername = currentUsername;
		this.codeService = new CodeService(this);
		this.shell.addListener(SWT.Close, e -> {
			codeService.stopService();
			this.shell.dispose();
		});
	}

	public SessionOpenDialog() {
		super("Sesion", false);
		this.currentUsername = "test";
		try {
			this.session = ApplicationSession.getInstance().getServer().getSessionText(3);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		this.codeService = new CodeService(this);
		this.shell.addListener(SWT.Close, e -> {
			codeService.stopService();
			this.shell.dispose();
		});
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new GridLayout(1, false));
		this.shell.setText("Session - " + session.getName());
		Composite headerComposite = new Composite(shell, SWT.NONE);
		headerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		headerComposite.setLayout(new FillLayout());
		Composite editorComposite = new Composite(headerComposite, SWT.NONE);
		Composite buttComposite = new Composite(headerComposite, SWT.RIGHT_TO_LEFT);
		buttComposite.setLayout(new RowLayout());
		Button runButton = new Button(buttComposite, SWT.NONE);
		runButton.setText("RUN");
		editorComposite.setLayout(new FillLayout());
		invitedCombo = new Combo(editorComposite, SWT.NONE);
		currentEditor = new Label(editorComposite, SWT.NONE);
		FontData[] fd = currentEditor.getFont().getFontData();
		fd[0].setHeight(13);
		currentEditor.setFont(new Font(currentEditor.getDisplay(), fd[0]));
		invitedCombo.setText(session.getOwnerName());
		for (User user : session.getUsers()) {
			invitedCombo.add(user.getUserName());
		}
		invitedCombo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				for (User user : session.getUsers()) {
					if (invitedCombo.getText().equals(user.getUserName())) {
						try {
							ApplicationSession.getInstance().getServer().updateSessionOwner(session,
									user.getUserName());
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		if (!this.currentUsername.equals(session.getOwnerName())) {
			invitedCombo.setVisible(false);
		}

		currentEditor.setText("___________________________");
		currentEditor.setText(session.getOwnerName());

		codeText = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		codeText.setLayoutData(new GridData(WIDTH, CODE_HEIGHT));
		codeText.setText(session.getText());

		Text resultText = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		resultText.setLayoutData(new GridData(WIDTH, RESULT_HEIGHT));
		resultText.setEditable(false);

		runButton.addListener(SWT.MouseUp, e -> {
			String code = codeText.getText();
			try {
				RunCodehandler runner = new RunCodehandler(code);
				runner.run();
				resultText.setText(runner.getResult());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		codeService.start();
	}

	public Shell getShell() {
		return this.shell;
	}

	public long getSessionId() {
		return session.getId();
	}

	public String getCurrentOwner() {
		return currentUsername;
	}

	public void setOwnerText(String ownerName) {
		this.currentEditor.setText(ownerName);
	}

	public int getCaretPosition() {
		return this.codeText.getCaretPosition();
	}

	public String getCodeText() {
		return codeText.getText();
	}

	public void setCodeText(String text) {
		this.codeText.setText(text);
	}

	public void setSelection(int position) {
		this.codeText.setSelection(position);
	}

	public void setComboVisible(boolean b) {
		this.invitedCombo.setVisible(b);
	}
}
