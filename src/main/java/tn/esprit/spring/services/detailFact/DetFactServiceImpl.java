package tn.esprit.spring.services.detailFact;

import java.util.List;

import tn.esprit.spring.entities.DetailFacture;


public interface DetFactServiceImpl {
	
	List<DetailFacture> retrieveAllPrdoDetails();

	DetailFacture addProdDetail(DetailFacture df);

	void deleteProdDetail(Long id);

	DetailFacture updateProdDetail(DetailFacture df);

	DetailFacture retrieveProdDetail(Long id);

}
