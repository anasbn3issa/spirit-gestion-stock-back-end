	package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

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
//@EqualsAndHashCode
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Produit implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idProduit; // Clé primaire
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
	@JoinColumn(name="idStock")
    private Stock stock;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name="idRayon")
    private Rayon rayon;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="produit")
	private Set<DetailFacture> factDetails;
	
	@NotNull
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Fournisseur> fournisseurs;
	
}
