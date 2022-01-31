package main.helpers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import main.model.User;
import main.server.ServerApplication;

public class InsertInDb {
	@SuppressWarnings("unused")
	private List<User> users = new ArrayList<User>();

	public static void main(String[] args) {
		InsertInDb db = new InsertInDb();
		try {
			ServerApplication.main(args);
			db.addGames();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void addGames() {

	}
}
