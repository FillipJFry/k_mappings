package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.FlywayMigration;
import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

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