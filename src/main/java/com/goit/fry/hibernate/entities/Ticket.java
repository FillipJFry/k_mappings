package com.goit.fry.hibernate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

	@Id
	private long id;
	@Column(name = "created_at")
	private LocalDateTime created_at;
	@Column(name = "client_id")
	private long clientId;
	@Column(name = "from_planet_id")
	private String fromPlanetId;
	@Column(name = "to_planet_id")
	private String toPlanetId;
}
