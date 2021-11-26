package tn.esprit.spring.services.livreur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Livraison;
import tn.esprit.spring.entities.Livreur;
import tn.esprit.spring.repositories.LivreurRepository;
import tn.esprit.spring.services.livraison.LvrServiceImpl;

@Slf4j
@Service
public class LvreurServiceImpl implements LivreurServiceImpl{

	@Autowired
	private LivreurRepository livreurRepository;
	
	@Override
	public List<Livreur> retrieveAllLivreurs() {
		// TODO Auto-generated method stub
		return (List<Livreur>) livreurRepository.findAll();
	}

	@Override
	public Livreur addLivreur(Livreur l) {
		// TODO Auto-generated method stub
		return livreurRepository.save(l);
	}

	@Override
	public void deleteLivreur(Long id) {
		// TODO Auto-generated method stub
		livreurRepository.deleteById(id);
	}

	@Override
	public Livreur updateLivreur(Livreur l) {
		// TODO Auto-generated method stub
		return livreurRepository.save(l);
	}

	@Override
	public Livreur retrieveLivreur(Long id) {
		// TODO Auto-generated method stub
		return livreurRepository.findById(id).orElse(null);
	}

}
