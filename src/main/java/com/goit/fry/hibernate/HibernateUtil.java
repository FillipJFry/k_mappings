package com.goit.fry.hibernate;

import com.goit.fry.hibernate.entities.*;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static HibernateUtil inst;

	@Getter
	private final SessionFactory sessionFactory;

	private HibernateUtil() {

		sessionFactory = new Configuration()
				.addAnnotatedClass(Client.class)
				.addAnnotatedClass(Planet.class)
				.addAnnotatedClass(Ticket.class)
				.buildSessionFactory();
	}

	public static HibernateUtil getInst() {

		if (inst == null) inst = new HibernateUtil();
		return inst;
	}

	public static void close() {

		if (inst != null) {
			inst.sessionFactory.close();
			inst = null;
		}
	}
}
