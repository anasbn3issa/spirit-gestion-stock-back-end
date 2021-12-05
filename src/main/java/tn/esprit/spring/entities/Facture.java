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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Facture implements Serializable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idFacture; // Clé primaire
	@NotNull
	float montantRemise;
	@NotNull
	float montantFacture;
	@NotNull
	@Temporal(TemporalType.DATE)
	Date dateFacture;
	@NotNull
	boolean active;
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy="facture")
	Set<DetailFacture> factDetails;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idClient")
	@JsonIgnore
    Client client;
	
}
