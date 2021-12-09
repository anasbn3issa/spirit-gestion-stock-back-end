package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Livraison implements Serializable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idLivraison; // Clé primaire
	
	@NotNull
	String code;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idLivreur")
    Livreur livreur;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	EtatLivraison etat;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	Date dateLivraisonPrevue;
	
	@Temporal(TemporalType.DATE)
	Date dateLivraison;
	
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "idFacture")
	Facture facture;

}
