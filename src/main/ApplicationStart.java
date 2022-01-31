package main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import main.gui.LogInDialog;

public class ApplicationStart {
	private static final ApplicationStart instance = new ApplicationStart();

	private Display display;
	private Shell shell;

	private ApplicationStart() {
		this.display = new Display();
		this.shell = new Shell(display);
	}

	public static ApplicationStart getInstance() {
		return instance;
	}

	private void start() {
		new LogInDialog("Code Interviewer - LOG IN").run();
		this.shell.setSize(1, 1);
		this.shell.open();
		this.shell.setVisible(false);
		this.shell.setEnabled(false);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public static void main(String[] args) {
		ApplicationStart application = ApplicationStart.getInstance();
		application.start();
	}

	public Display getDisplay() {
		return display;
	}

	public Shell getShell() {
		return shell;
	}

}
