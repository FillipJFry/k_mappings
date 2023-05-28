package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.TransactionWrapper;
import com.goit.fry.hibernate.entities.Client;
import com.goit.fry.hibernate.entities.Planet;
import com.goit.fry.hibernate.entities.Ticket;

import java.time.LocalDateTime;

public class TicketCrudService extends BasicService<Ticket, Long> {

	public TicketCrudService() {

		super(Ticket.class);
	}

	public Ticket create(Client client, Planet fromPlanet, Planet toPlanet) {

		Ticket ticket = Ticket.builder()
							.client(client)
							.fromPlanet(fromPlanet)
							.toPlanet(toPlanet)
							.createdAt(LocalDateTime.now())
							.build();
		return create(ticket);
	}

	public void update(long id, Client newClient) {

		try(TransactionWrapper transaction = new TransactionWrapper()) {

			Ticket ticket = transaction.getSession().get(Ticket.class, id);
			ticket.setClient(newClient);
			transaction.getSession().merge(ticket);
			transaction.commit();
		}
	}
}
