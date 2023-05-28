package com.goit.fry.hibernate.services;

import org.hibernate.Session;

import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Client;
import org.hibernate.query.Query;

public class ClientCrudService extends BasicService<Client, Long> {

	public ClientCrudService() {

		super(Client.class);
	}

	public Client create(String clientName) {

		Client client = Client.builder().name(clientName).build();
		return create(client);
	}

	public Client readByName(String clientName) {

		Client v;
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Query<Client> query = session.createQuery("from Client where name=:cliName", Client.class);
			query.setParameter("cliName", clientName);
			v = query.uniqueResult();
		}
		return v;
	}

	public void update(long id, String newName) {

		Client client = Client.builder().id(id).name(newName).build();
		update(client);
	}
}