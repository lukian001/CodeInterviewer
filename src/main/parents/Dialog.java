package main.parents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import main.ApplicationStart;
import main.enums.Styles;

public abstract class Dialog {
	protected Shell shell;

	public Dialog(String dialogTitle, boolean hardClose) {
		this.shell = new Shell(ApplicationStart.getInstance().getDisplay(), Styles.SHELL_SIZE.getStyle());
		this.shell.setText(dialogTitle);

		if (hardClose) {
			this.shell.addListener(SWT.Close, e -> {
				ApplicationStart.getInstance().getShell().dispose();
			});
		}
	}

	public abstract void createDialog();

	public void run() {
		createDialog();
		this.shell.pack();
		this.shell.open();
	}

	public void dispose() {
		this.shell.dispose();
	}
}
