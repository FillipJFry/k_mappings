package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.FlywayMigration;
import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Client;
import com.goit.fry.hibernate.entities.Planet;
import com.goit.fry.hibernate.entities.Ticket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientCrudServiceTest {

	private ClientCrudService srv;

	@BeforeAll
	static void init() {

		Path dbPath = Path.of("spacetravel.mv.db");
		try {
			Files.deleteIfExists(dbPath);
			FlywayMigration.performMigration("./spacetravel", "migration");
		}
		catch (Exception e) {

			e.printStackTrace();
		}
	}

	@BeforeEach
	void initSrv() {

		srv = new ClientCrudService();
	}

	@Test
	void createDoesNotThrow() {

		assertDoesNotThrow(() -> srv.create("John Doe"));
	}

	@Test
	void createReturnsCorrectObj() {

		Client client = srv.create("Alice");

		assertNotNull(client);
		assertTrue(client.getId() >= 0);
		assertEquals("Alice", client.getName());
	}

	@Test
	void read() {

		long id = srv.create("Bob").getId();
		Client client = srv.readById(id);

		assertEquals(id, client.getId());
		assertEquals("Bob", client.getName());
	}

	@Test
	void readAll() {

		srv.deleteAll();
		srv.create("Alice");
		srv.create("Bob");
		srv.create("Mallory");
		List<Client> clients = srv.readAll();

		clients.sort(new Comparator<Client>() {
			@Override
			public int compare(Client l, Client r) {
				return (int)(l.getId() - r.getId());
			}
		});

		assertEquals(3, clients.size());
		assertEquals("Alice", clients.get(0).getName());
		assertEquals("Bob", clients.get(1).getName());
		assertEquals("Mallory", clients.get(2).getName());
	}

	@Test
	void getTicketsList() {

		TicketCrudService ticket_srv = new TicketCrudService();
		Client musk = srv.readByName("Elon Musk");
		assertNotNull(musk.getTickets());
		assertEquals(1, musk.getTickets().size());
		Ticket t1 = musk.getTickets().get(0);

		PlanetCrudService planet_srv = new PlanetCrudService();
		Planet from = planet_srv.readById("MARS");
		Planet to = planet_srv.readById("ERTH");
		Planet to2 = planet_srv.readById("TTN");

		Ticket t2 = ticket_srv.create(musk, from, to);
		Ticket t3 = ticket_srv.create(musk, to, to2);

		assertEquals(1, musk.getTickets().size());
		musk = srv.readByName("Elon Musk");
		List<Ticket> tickets = musk.getTickets();
		assertNotNull(tickets);
		assertEquals(3, tickets.size());

		tickets.sort(new Comparator<Ticket>() {
			@Override
			public int compare(Ticket l, Ticket r) {

				return (int)(l.getId() - r.getId());
			}
		});
		assertEquals(t1.getId(), tickets.get(0).getId());
		assertEquals(t2.getId(), tickets.get(1).getId());
		assertEquals(t3.getId(), tickets.get(2).getId());
	}

	@Test
	void update() {

		long id = srv.create("Eve").getId();
		srv.update(id, "Trent");
		Client client = srv.readById(id);

		assertEquals("Trent", client.getName());
	}

	@Test
	void delete() {

		long id = srv.create("Mallory").getId();
		assertDoesNotThrow(() -> srv.delete(id));
		assertNull(srv.readById(id));
	}

	@AfterAll
	static void releaseFactory() {

		HibernateUtil.close();
	}
}