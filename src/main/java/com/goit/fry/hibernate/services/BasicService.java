package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.TransactionWrapper;
import org.hibernate.Session;

import java.util.List;

public class BasicService<TEntity, TId> {

	private final Class<TEntity> cl;

	protected BasicService(Class<TEntity> cl) {

		this.cl = cl;
	}

	protected TEntity create(TEntity v) {

		try(TransactionWrapper transaction = new TransactionWrapper()) {

			transaction.getSession().persist(v);
			transaction.commit();
		}
		return v;
	}

	public TEntity readById(TId id) {

		TEntity v;
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			v = session.get(cl, id);
		}
		return v;
	}

	public List<TEntity> readAll() {

		List<TEntity> list;
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			list = session.createQuery("from " + cl.getSimpleName(), cl).list();
		}
		return list;
	}

	public void update(TEntity v) {

		try(TransactionWrapper transaction = new TransactionWrapper()) {

			transaction.getSession().merge(v);
			transaction.commit();
		}
	}

	protected void delete(TId id) {

		try(TransactionWrapper transaction = new TransactionWrapper()) {

			TEntity v = transaction.getSession().get(cl, id);
			transaction.getSession().remove(v);
			transaction.commit();
		}
	}

	public void deleteAll() {

		try(TransactionWrapper transaction = new TransactionWrapper()) {

			transaction.getSession()
					.createQuery("delete " + cl.getSimpleName(), null)
					.executeUpdate();
			transaction.commit();
		}
	}
}
