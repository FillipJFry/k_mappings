package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.FlywayMigration;
import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Planet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PlanetCrudServiceTest {
	private PlanetCrudService srv;

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

		srv = new PlanetCrudService();
	}

	@Test
	void createDoesNotThrow() {

		assertDoesNotThrow(() -> srv.create("PLT", "Pluto"));
	}

	@Test
	void createReturnsCorrectObj() {

		Planet planet = srv.create("SAT", "Saturn");

		assertNotNull(planet);
		assertEquals("SAT", planet.getId());
		assertEquals("Saturn", planet.getName());
	}

	@Test
	void read() {

		String id = srv.create("NEPT", "Neptune").getId();
		Planet planet = srv.readById(id);

		assertEquals(id, planet.getId());
		assertEquals("Neptune", planet.getName());
	}

	@Test
	void update() {

		String id = srv.create("XYZ", "Omuamua").getId();
		srv.update(id, "Nibiru");
		Planet planet = srv.readById(id);

		assertEquals("Nibiru", planet.getName());
	}

	@Test
	void delete() {

		String id = srv.create("CLST", "Callisto").getId();
		assertDoesNotThrow(() -> srv.delete(id));
		assertNull(srv.readById(id));
	}

	@AfterAll
	static void releaseFactory() {

		HibernateUtil.close();
	}
}