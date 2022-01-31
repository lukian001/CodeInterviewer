package main.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import main.server.persistence.SessionFactoryProvider;
import main.server.services.Service;
import main.server.services.SessionCheckerService;

public class ServerApplication {

	private Service[] services = { new SessionCheckerService() };

	private ServerApplication() throws RemoteException {
	}

	public void startServer() {
		try {
			System.err.println("Starting server...");

			SessionFactoryProvider.getSessionFactory();
			LocateRegistry.createRegistry(1099);
			Naming.rebind("//localhost/MyServer", new ServerCallsImpl());

			System.err.println("Server ready!");

			System.err.println("Starting services...");
			createServices();
		} catch (Exception e) {
			System.out.println("Server exception: ");
			e.printStackTrace();
		}
	}

	public static void openDialog() {
		Display dsp = new Display();
		Shell shell = new Shell(dsp);
		shell.setText("Server Running");

		shell.setLayout(new GridLayout(1, false));

		Button startServer = new Button(shell, SWT.NONE);
		Button stopServer = new Button(shell, SWT.NONE);
		Label colorLabel = new Label(shell, SWT.MULTI);
		colorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		colorLabel.setBackground(dsp.getSystemColor(SWT.COLOR_RED));
		startServer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		startServer.setText("Start Server!");
		startServer.addListener(SWT.MouseUp, e -> {
			ServerApplication server;
			try {
				server = new ServerApplication();
				server.startServer();

				colorLabel.setBackground(dsp.getSystemColor(SWT.COLOR_GREEN));
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		stopServer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		stopServer.setText("Stop Server!");
		stopServer.addListener(SWT.MouseUp, e -> {
			System.exit(1);
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!dsp.readAndDispatch()) {
				dsp.sleep();
			}
		}
	}

	private void createServices() {
		for (Service service : services) {
			service.start();
		}
	}

	public static void main(String[] args) throws RemoteException {
		ServerApplication.openDialog();
	}
}
