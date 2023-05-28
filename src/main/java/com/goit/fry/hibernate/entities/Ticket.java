package com.goit.fry.hibernate.entities;

import jakarta.persistence.*;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "from_planet_id", nullable = false)
	private Planet fromPlanet;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "to_planet_id", nullable = false)
	private Planet toPlanet;
}
