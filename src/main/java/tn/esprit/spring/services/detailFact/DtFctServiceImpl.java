package tn.esprit.spring.services.detailFact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.DetailFacture;
import tn.esprit.spring.repositories.DetailFactRepository;

@Service
public class DtFctServiceImpl implements DetFactServiceImpl{
	
	@Autowired
	private DetailFactRepository detailFactRepository;

	@Override
	public List<DetailFacture> retrieveAllFctDetails() {
		return (List<DetailFacture>) detailFactRepository.findAll();
	}

	@Override
	public DetailFacture addFctDetail(DetailFacture df) {
		return detailFactRepository.save(df);
	}

	@Override
	public void deleteFctDetail(Long id) {
		detailFactRepository.deleteById(id);
		
	}

	@Override
	public DetailFacture updateFctDetail(DetailFacture df) {
		return detailFactRepository.save(df);
	}

	@Override
	public DetailFacture retrieveFctDetail(Long id) {
		return detailFactRepository.findById(id).orElse(null);
	}
	

}
