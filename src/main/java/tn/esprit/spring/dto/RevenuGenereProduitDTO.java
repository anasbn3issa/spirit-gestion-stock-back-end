package tn.esprit.spring.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RevenuGenereProduitDTO {
	private Date startDate;
	private Date endDate;
	private Long idProduit;
}
