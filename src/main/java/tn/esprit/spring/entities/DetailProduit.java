package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
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
public class DetailProduit implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idDetailProduit; // Clé primaire
	@NotNull
	@Temporal(TemporalType.DATE)
	Date dateCreation;
	@NotNull
	@Temporal(TemporalType.DATE)
	Date dateDernièreModification;
	@NotNull
	@Enumerated(EnumType.STRING)
	CategorieProduit categorieProduit;
	
	@JsonIgnore
	@NotNull
	@OneToOne(mappedBy = "detailProduit")
    Produit produit;
}