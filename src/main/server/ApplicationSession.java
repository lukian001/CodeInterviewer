package main.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ApplicationSession {
	private static ApplicationSession applicationSession = null;

	private ServerCalls serverCall = null;

	private ApplicationSession() throws RemoteException {
		try {
			serverCall = (ServerCalls) Naming.lookup("//localhost/MyServer");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public static ApplicationSession getInstance() throws RemoteException {
		if (applicationSession == null) {
			applicationSession = new ApplicationSession();
		}

		return applicationSession;
	}

	public ServerCalls getServer() {
		return serverCall;
	}
}
