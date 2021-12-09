package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table( name = "Client")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="idClient")
	Long idClient; // Clé primaire
	@NotNull
	String nom;
	@NotNull
	String prenom;
	@NotNull
	String email;
	@NotNull
	String password;
	@NotNull
	@Temporal(TemporalType.DATE)
	Date dateNaissance;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	Profession profession;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	CategorieClient categorieClient;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="client", fetch = FetchType.LAZY)
	Set<Facture> factures;
	
	
}
