package com.goit.fry.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionWrapper implements AutoCloseable {

	private final Session session;
	private final Transaction transaction;
	private boolean commited = true;

	public TransactionWrapper() {

		session = HibernateUtil.getInst()
					.getSessionFactory()
					.openSession();
		transaction = session.beginTransaction();
		commited = false;
	}

	public Session getSession() {

		return session;
	}

	public void commit() {

		transaction.commit();
		commited = true;
	}

	@Override
	public void close() {

		if (!commited)
			transaction.rollback();
		session.close();
	}
}
