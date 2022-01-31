package main.gui.service;

import java.rmi.RemoteException;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;

import main.gui.MainDialog;
import main.model.User;
import main.server.ApplicationSession;

public class RefillTableService extends Service {

	private MainDialog dlg;

	public RefillTableService(MainDialog dlg) {
		super("Refill table", 10);
		this.dlg = dlg;
	}

	@Override
	protected void doServiceAction() {
		try {
			User usr = ApplicationSession.getInstance().getServer().getUserByUsername(dlg.getUser().getUserName());

			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					dlg.getTable().redraw(usr.getSessions());
				}
			});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
