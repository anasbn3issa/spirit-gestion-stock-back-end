package tn.esprit.spring.entities;

import java.io.Serializable;
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
@EqualsAndHashCode
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailFacture implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idDetailFacture; // Clé primaire
	@NotNull
	float prixTotal;
	@NotNull
	float montantRemise;
	@NotNull
	Integer qte;
	@NotNull
	Integer pourcentageRemise;
	@NotNull
	@ManyToOne
	@JoinColumn(name="idProduit")
    private Produit produit;
	@NotNull
	@ManyToOne
	@JoinColumn(name="idFacture")
    private Facture facture;
	
}
