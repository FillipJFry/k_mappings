package com.goit.fry.hibernate.services;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Client;

public class ClientCrudService {

	public Client create(String clientName) {

		Client client = Client.builder().name(clientName).build();
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();
			session.persist(client);
			transaction.commit();
		}
		return client;
	}

	public Client readById(long id) {

		Client client;
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			client = session.get(Client.class, id);
		}
		return client;
	}

	public void update(long id, String newName) {

		Client client = Client.builder().id(id).name(newName).build();
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();
			session.merge(client);
			transaction.commit();
		}
	}

	public void delete(long id) {

		Client client = Client.builder().id(id).build();
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();
			session.remove(client);
			transaction.commit();
		}
	}
}