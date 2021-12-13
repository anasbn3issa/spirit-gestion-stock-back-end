package tn.esprit.spring.services.detailFact;

import java.util.Date;
import java.util.List;

import tn.esprit.spring.entities.DetailFacture;


public interface DetFactServiceImpl {
	
	List<DetailFacture> retrieveAllFctDetails();

	DetailFacture addFctDetail(DetailFacture df);

	void deleteFctDetail(Long id);

	DetailFacture updateFctDetail(DetailFacture df);

	DetailFacture retrieveFctDetail(Long id);
	
	float getRevenuBrutProduit(Long idProduit, Date startDate, Date endDate);

}
