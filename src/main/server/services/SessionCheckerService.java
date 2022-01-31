package main.server.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.TimestampType;

import main.server.persistence.WithSessionAndTransaction;

public class SessionCheckerService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2613276201052523976L;

	public SessionCheckerService() {
		super("Session Checker Service", 10);
	}

	@Override
	protected void doServiceAction() {
		new WithSessionAndTransaction<Object>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				@SuppressWarnings("unchecked")
				Query<main.model.Session> sessionQuery = session
						.getNamedQuery(main.model.Session.GET_TODAYS_SESSION_BY_STATUS);

				Calendar calStart = Calendar.getInstance();
				calStart.set(Calendar.HOUR_OF_DAY, 0);
				calStart.set(Calendar.MINUTE, 0);
				calStart.set(Calendar.SECOND, 0);
				calStart.set(Calendar.MILLISECOND, 0);

				sessionQuery.setParameter("todayMidnight", calStart.getTime(), TimestampType.INSTANCE);
				calStart.add(Calendar.DAY_OF_MONTH, 1);
				sessionQuery.setParameter("tomorrowMidnight", calStart.getTime(), TimestampType.INSTANCE);

				List<main.model.Session> sessions = sessionQuery.list();

				for (main.model.Session ses : sessions) {
					Date date = new Date();
					if (date.getTime() >= ses.getStartingTime().getTime()
							&& date.getTime() <= ses.getEndingTime().getTime()) {
						ses.setStatus(main.model.SessionStatus.ON);
						session.update(ses);
					} else if (date.getTime() > ses.getEndingTime().getTime()) {
						ses.setStatus(main.model.SessionStatus.DONE);
						session.update(ses);
					}
				}
			}
		}.run();
	}

}
