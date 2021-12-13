package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
public class Livreur implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idLivreur; // Clé primaire
	
	@NotNull
	String code;
	
	@NotNull
	String nom;
	
	@NotNull
	String email;
	
	@NotNull
	Integer telephone;
	
	@NotNull
	String addresse;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	EtatLivreur etat = EtatLivreur.Actif;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	Date dateAdhesion;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="livreur", fetch = FetchType.EAGER)
	private Set<Livraison> livraisonList;

}
