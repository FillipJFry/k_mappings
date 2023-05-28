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

import static org.junit.jupiter.api.Assertions.*;

class TicketCrudServiceTest {

	private TicketCrudService srv;

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

		srv = new TicketCrudService();
	}

	@Test
	void create() {

		ClientCrudService clientSrv = new ClientCrudService();
		Client fry = clientSrv.readByName("Fry");

		PlanetCrudService planet_srv = new PlanetCrudService();
		Planet from = planet_srv.readById("ERTH");
		Planet to = planet_srv.readById("MARS");

		assertDoesNotThrow(() -> srv.create(fry, from, to));
	}

	@Test
	void createNonExitingClientThrows() {

		PlanetCrudService planet_srv = new PlanetCrudService();
		Planet from = planet_srv.readById("ERTH");
		Planet to = planet_srv.readById("MARS");

		assertThrows(Exception.class,
				() -> srv.create(Client.builder().name("Joe").build(), from, to));
	}

	@Test
	void createNullClientThrows() {

		PlanetCrudService planet_srv = new PlanetCrudService();
		Planet from = planet_srv.readById("ERTH");
		Planet to = planet_srv.readById("MARS");

		assertThrows(Exception.class, () -> srv.create(null, from, to));
	}

	@Test
	void update() {

		ClientCrudService clientSrv = new ClientCrudService();
		Client fry = clientSrv.readByName("Fry");

		PlanetCrudService planet_srv = new PlanetCrudService();
		Planet from = planet_srv.readById("CERS");
		Planet to = planet_srv.readById("TTN");

		Ticket t = srv.create(fry, from, to);
		Client vasya = clientSrv.readByName("Vasya");

		srv.update(t.getId(), vasya);
		t = srv.readById(t.getId());
		assertEquals("Vasya", t.getClient().getName());
	}

	@AfterAll
	static void releaseFactory() {

		HibernateUtil.close();
	}
}