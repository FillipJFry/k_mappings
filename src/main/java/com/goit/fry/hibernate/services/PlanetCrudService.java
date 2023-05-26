package com.goit.fry.hibernate.services;

import com.goit.fry.hibernate.HibernateUtil;
import com.goit.fry.hibernate.entities.Planet;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PlanetCrudService {
	public Planet create(String id, String planetName) {

		Planet planet = Planet.builder().id(id).name(planetName).build();
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();
			session.persist(planet);
			transaction.commit();
		}
		return planet;
	}

	public Planet readById(String id) {

		Planet planet;
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			planet = session.get(Planet.class, id);
		}
		return planet;
	}

	public void update(String id, String newName) {

		Planet planet = Planet.builder().id(id).name(newName).build();
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();
			session.merge(planet);
			transaction.commit();
		}
	}

	public void delete(String id) {

		Planet planet = Planet.builder().id(id).build();
		try(Session session = HibernateUtil.getInst().getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();
			session.remove(planet);
			transaction.commit();
		}
	}
}
