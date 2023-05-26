package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TicketCrudService {

	public Ticket create() {

		return null;
	}

	public Ticket readById(long id) {

		Ticket ticket;
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			ticket = session.get(Ticket.class, id);
		}
		return ticket;
	}

	public void update(long id) {

	}

	public void delete(long id) {

		Ticket ticket = Ticket.builder().id(id).build();
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();
			session.remove(ticket);
			transaction.commit();
		}
	}
}
