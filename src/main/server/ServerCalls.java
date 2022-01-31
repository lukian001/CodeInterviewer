package main.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import main.model.Session;
import main.model.User;

public interface ServerCalls extends Remote {

	void addUser(User createdUser) throws RemoteException;

	User getUserByUsername(String username) throws RemoteException;

	User getUserByEmail(String email) throws RemoteException;

	User checkLoginCredentials(String userName, String password) throws RemoteException;

	void updateUserEmail(User user, String text) throws RemoteException;

	void updateUserPassword(User user, String password) throws RemoteException;

	void addSession(Session session, User user, List<User> invitedUsers) throws RemoteException;

	Session getSessionText(long sessionId) throws RemoteException;

	void setSessionText(long sessionID, String text) throws RemoteException;

	void updateSessionOwner(Session session, String userName) throws RemoteException;
}
