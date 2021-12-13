package tn.esprit.spring.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tn.esprit.spring.entities.CategorieClient;

@Getter
@Setter
@ToString
public class ChiffreAffaireParCategorieClientDTO {
	private Date startDate;
	private Date endDate;
	private CategorieClient categorieClient;

}
