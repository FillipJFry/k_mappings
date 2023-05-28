package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Planet;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PlanetCrudService extends BasicService<Planet, String> {

	public PlanetCrudService() {

		super(Planet.class);
	}

	public Planet create(String id, String planetName) {

		Planet planet = Planet.builder().id(id).name(planetName).build();
		return create(planet);
	}

	public void update(String id, String newName) {

		Planet planet = Planet.builder().id(id).name(newName).build();
		update(planet);
	}
}
