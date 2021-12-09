package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
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
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Reclamation implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long idRec; // Clé primaire
	@NotNull
	String description;
	
	@NotNull
	String status;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date dateCreation;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	Date dateResolution;
	
	@NotNull
	String TypeReclamation;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name="idProduit")
    private Produit produit;
	
}
	