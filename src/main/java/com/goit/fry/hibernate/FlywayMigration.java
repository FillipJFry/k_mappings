package com.goit.fry.hibernate;

import org.flywaydb.core.Flyway;

public class FlywayMigration {

	public static void main(String[] args) {

		performMigration("./spacetravel", "migration");
	}

	public static void performMigration(String dbPath, String sqlFolder) {

		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:h2:" + dbPath, "sa", "")
				.baselineOnMigrate(true)
				.table("flyway_schema_history")
				.locations(sqlFolder)
				.load();

		flyway.migrate();
	}
}
