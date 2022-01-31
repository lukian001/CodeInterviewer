package main.handlers;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import main.exceptions.BlankFieldException;
import main.exceptions.InvalidDateException;
import main.exceptions.InviteOnePersonException;
import main.model.Session;
import main.model.User;
import main.server.ApplicationSession;

public class AddSessionHandler {

	private String sessionName;
	private String startTime;
	private String duration;
	private List<User> invitedUsers;
	private User user;

	public AddSessionHandler(User user, String sessionName, String startTime, String duration,
			List<User> invitedUsers) {
		this.user = user;
		this.sessionName = sessionName;
		this.startTime = startTime;
		this.duration = duration;
		this.invitedUsers = invitedUsers;
	}

	public void process() throws BlankFieldException, InvalidDateException, InviteOnePersonException {
		if (sessionName.isBlank() || duration.isBlank()) {
			throw new BlankFieldException("Please fill all the fields!");
		} else if (!startTime.contains("-")) {
			throw new InvalidDateException("Please select a valid date!");
		} else if (invitedUsers.isEmpty()) {
			throw new InviteOnePersonException("Please invite atleast one person!");
		}

		Session session = new Session();
		session.setName(sessionName);
		try {
			Date date = new SimpleDateFormat("MM/dd/yyyy - hh:mm").parse(startTime);
			session.setStartingTime(date);
			Date endDate = new Date();
			endDate.setTime(date.getTime() + Integer.parseInt(duration) * 1000 * 60);
			session.setEndingTime(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			ApplicationSession.getInstance().getServer().addSession(session, user, invitedUsers);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
