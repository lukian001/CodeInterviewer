package main.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import main.model.User;
import main.server.persistence.WithSessionAndTransaction;

public class ServerCallsImpl extends UnicastRemoteObject implements ServerCalls {

	public ServerCallsImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void addUser(User user) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				session.save(user);
			}
		};
		action.run();
	}

	@Override
	public User getUserByUsername(String username) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				@SuppressWarnings("unchecked")
				Query<User> searchQuery = session.getNamedQuery(User.GET_USER_BY_USERNAME);
				searchQuery.setParameter("username", username);

				User user = searchQuery.uniqueResult();
				setReturnValue(user);
			}
		};
		return action.run();
	}

	@Override
	public User getUserByEmail(String email) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				@SuppressWarnings("unchecked")
				Query<User> searchQuery = session.getNamedQuery(User.GET_USER_BY_EMAIL);
				searchQuery.setParameter("email", email);

				User user = searchQuery.uniqueResult();
				setReturnValue(user);
			}
		};
		return action.run();
	}

	@Override
	public User checkLoginCredentials(String username, String password) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				@SuppressWarnings("unchecked")
				Query<User> searchQuery = session.getNamedQuery(User.GET_USER_BY_USERNAME);
				searchQuery.setParameter("username", username);

				User user = searchQuery.uniqueResult();

				if (user != null && password.equals(user.getPassword())) {
					setReturnValue(user);
				} else {
					setReturnValue(null);
				}
			}
		};
		return action.run();
	}

	@Override
	public void updateUserEmail(User user, String email) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User userN = session.load(User.class, user.getId());
				userN.setEmail(email);
				session.update(userN);
			}
		};
		action.run();
	}

	@Override
	public void updateUserPassword(User user, String password) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User userN = session.load(User.class, user.getId());
				userN.setPassword(password);
				session.update(userN);
			}
		};
		action.run();
	}

	@Override
	public void addSession(main.model.Session createdSession, User user, List<User> invitedUsers)
			throws RemoteException {
		WithSessionAndTransaction<main.model.Session> action = new WithSessionAndTransaction<>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				List<User> invited = new ArrayList<User>();
				invited.add(session.load(User.class, user.getId()));

				for (User usr : invitedUsers) {
					invited.add(session.load(User.class, usr.getId()));
				}

				createdSession.setOwnerName(user.getUserName());
				createdSession.setUsers(invited);
				session.save(createdSession);
			}
		};
		action.run();
	}

	@Override
	public main.model.Session getSessionText(long sessionId) throws RemoteException {
		WithSessionAndTransaction<main.model.Session> action = new WithSessionAndTransaction<main.model.Session>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				@SuppressWarnings("unchecked")
				Query<main.model.Session> sessionQuery = session.getNamedQuery(main.model.Session.GET_SESSION_BY_ID);
				sessionQuery.setParameter("id", sessionId);

				main.model.Session ses = sessionQuery.uniqueResult();
				setReturnValue(ses);
			}
		};

		return action.run();
	}

	@Override
	public void setSessionText(long sessionID, String text) throws RemoteException {
		WithSessionAndTransaction<main.model.Session> action = new WithSessionAndTransaction<main.model.Session>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				main.model.Session ses = session.load(main.model.Session.class, sessionID);
				ses.setText(text);
				session.update(ses);
			}
		};
		action.run();
	}

	@Override
	public void updateSessionOwner(main.model.Session sessionId, String userName) throws RemoteException {
		WithSessionAndTransaction<main.model.Session> action = new WithSessionAndTransaction<main.model.Session>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				main.model.Session ses = session.load(main.model.Session.class, sessionId.getId());
				ses.setOwnerName(userName);
				session.update(ses);
			}
		};
		action.run();
	}
}
