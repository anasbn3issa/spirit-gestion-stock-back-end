	package tn.esprit.spring.entities;

import java.io.Serializable;
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
public class Produit implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idProduit; // Clé primaire
	@JsonIgnore
	@NotNull
	@OneToOne
    @JoinColumn(name = "idDetailProduit")
	DetailProduit detailProduit;
	@NotNull
	String code;
	@NotNull
	String libelle;
	@NotNull
	float prixUnitaire;
	
	@NotNull
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="idStock")
    private Stock stock;
	
	@JsonIgnore
	@NotNull
	@ManyToOne
    @JoinColumn(name="idRayon")
    private Rayon rayon;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="produit")
	private Set<DetailFacture> factDetails;
	
	@JsonIgnore
	@NotNull
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Fournisseur> fournisseurs;
	
}
