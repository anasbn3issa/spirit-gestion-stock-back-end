package tn.esprit.spring.services.detailProd;

import java.util.List;

import tn.esprit.spring.entities.DetailProduit;


public interface DetProdServiceImpl {
	
	List<DetailProduit> retrieveAllPrdoDetails();

	DetailProduit addProdDetail(DetailProduit c);

	void deleteProdDetail(Long id);

	DetailProduit updateProdDetail(DetailProduit c);

	DetailProduit retrieveProdDetail(Long id);

}
