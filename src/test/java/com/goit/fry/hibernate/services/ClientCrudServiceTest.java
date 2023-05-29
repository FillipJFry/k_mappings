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
import java.util.List;
import java.util.Set;

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

		clients.sort((l, r) -> (int)(l.getId() - r.getId()));

		assertEquals(3, clients.size());
		assertEquals("Alice", clients.get(0).getName());
		assertEquals("Bob", clients.get(1).getName());
		assertEquals("Mallory", clients.get(2).getName());
	}

	@Test
	void getTicketsList() {

		TicketCrudService ticket_srv = new TicketCrudService();
		Client musk = srv.readByName("Elon Musk");
		Set<Ticket> tickets = musk.getTickets();
		assertNotNull(tickets);
		assertEquals(1, tickets.size());
		Ticket t1 = tickets.iterator().next();

		PlanetCrudService planet_srv = new PlanetCrudService();
		Planet from = planet_srv.readById("MARS");
		Planet to = planet_srv.readById("ERTH");
		Planet to2 = planet_srv.readById("TTN");

		Ticket t2 = ticket_srv.create(musk, from, to);
		Ticket t3 = ticket_srv.create(musk, to, to2);

		assertEquals(1, musk.getTickets().size());
		musk = srv.readByName("Elon Musk");
		tickets = musk.getTickets();
		assertNotNull(tickets);
		assertEquals(3, tickets.size());

		assertTrue(tickets.contains(t1));
		assertTrue(tickets.contains(t2));
		assertTrue(tickets.contains(t3));
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