package com.goit.fry.hibernate.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planets")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"ticketsFrom", "ticketsTo"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planet {

	@Id
	@Column(name = "id", length = 10)
	@EqualsAndHashCode.Include
	private String id;
	@Column(name = "name", length = 500)
	private String name;

	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "from_planet_id")
	private Set<Ticket> ticketsFrom = new HashSet<>();

	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "to_planet_id")
	private Set<Ticket> ticketsTo = new HashSet<>();
}
