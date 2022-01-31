package main.server.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryProvider {
	private static SessionFactory factory;

	public static SessionFactory getSessionFactory() {
		if (factory == null) {

			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			try {
				factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			} catch (Exception e) {
				StandardServiceRegistryBuilder.destroy(registry);
			}

		}

		return factory;
	}
}
