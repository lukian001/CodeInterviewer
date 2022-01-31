package main.gui.service;

import java.rmi.RemoteException;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;

import main.gui.SessionOpenDialog;
import main.model.Session;
import main.server.ApplicationSession;

public class CodeService extends Service {

	public CodeService(SessionOpenDialog dlg) {
		super("Code Service", 1);

		this.dlg = dlg;
	}

	private SessionOpenDialog dlg;

	@Override
	protected void doServiceAction() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					Session ses = ApplicationSession.getInstance().getServer().getSessionText(dlg.getSessionId());
					int position = dlg.getCaretPosition();
					dlg.setOwnerText(ses.getOwnerName());
					if (ses.getOwnerName().equals(dlg.getCurrentOwner())) {
						ApplicationSession.getInstance().getServer().setSessionText(ses.getId(), dlg.getCodeText());
						dlg.setComboVisible(true);
					} else {
						dlg.setCodeText(
								ApplicationSession.getInstance().getServer().getSessionText(ses.getId()).getText());
						dlg.setSelection(position);
						dlg.setComboVisible(false);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected boolean returnSecondCondition() {
		try {
			dlg.getShell().getDisplay();
		} catch (SWTException e) {
			return false;
		}
		return true;
	}

}
