package main.server.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class WithSessionAndTransaction<T> {
	protected T returnValue;
	boolean commit = true;

	public T run() {
		SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		executeBusinessLogic(session);

		if (commit) {
			tx.commit();
		}
		session.close();

		return returnValue;
	}

	protected abstract void executeBusinessLogic(Session session);

	protected void setReturnValue(T returnValue) {
		this.returnValue = returnValue;
	}

	public void doNotCommit() {
		this.commit = false;
	}
}
