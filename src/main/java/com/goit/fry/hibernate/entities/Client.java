package com.goit.fry.hibernate.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Data
@EqualsAndHashCode(exclude = {"name", "tickets"})
@ToString(exclude = {"tickets"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="name", length = 200)
	private String name;

	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Set<Ticket> tickets = new HashSet<>();
}
