package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@NoArgsConstructor
@Table( name = "Client")
public class Client implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idClient; // Clé primaire
	private String nom;
	private String prenom;
	private String email;
	/*@JsonIgnore
	private String password;*/
	private String photo;
	private float IncomeInTheLast24h;
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	//@Enumerated(EnumType.STRING)
	//private Profession profession;
	private String profession;
	@Enumerated(EnumType.STRING)
	private CategorieClient categorieClient;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="client", fetch = FetchType.LAZY)
	Set<Facture> factures;
	
	
	
	
}
