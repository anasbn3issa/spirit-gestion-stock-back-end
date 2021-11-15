package tn.esprit.spring.services.detailProd;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.DetailProduit;
import tn.esprit.spring.repositories.DetailProdRepository;

@Service
public class DtPrdServiceImpl implements DetProdServiceImpl{
	
	@Autowired
	private DetailProdRepository detailProdRepository;

	@Override
	public List<DetailProduit> retrieveAllPrdoDetails() {
		// TODO Auto-generated method stub
		return (List<DetailProduit>) detailProdRepository.findAll();
	}

	@Override
	public DetailProduit addProdDetail(DetailProduit c) {
		// TODO Auto-generated method stub
		Date date = new Date(System.currentTimeMillis());
		c.setDateCreation(date);
		c.setDateDernièreModification(date);

		return detailProdRepository.save(c);
	}

	@Override
	public void deleteProdDetail(Long id) {
		detailProdRepository.deleteById(id);
		
	}

	@Override
	public DetailProduit updateProdDetail(DetailProduit c) {
		// TODO Auto-generated method stub
		return detailProdRepository.save(c);
	}

	@Override
	public DetailProduit retrieveProdDetail(Long id) {
		// TODO Auto-generated method stub
		return detailProdRepository.findById(id).orElse(null);
	}
	

}
