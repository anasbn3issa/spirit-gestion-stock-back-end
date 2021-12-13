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
public class Stock implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idStock; // Clé primaire
	@NotNull
	Integer qte;
	@NotNull
	Integer qteMin;
	@NotNull
	String libelleStock;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER ,mappedBy="stock")
	@JsonIgnore 
	private Set<Produit> produits;	
	
}
