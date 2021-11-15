package tn.esprit.spring.services.rayon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Rayon;
import tn.esprit.spring.repositories.RayonRepository;

@Service
public class RynServiceImpl implements RayonServiceImpl{
	
	@Autowired
	private RayonRepository rayonRepository;

	@Override
	public List<Rayon> retrieveAllRayons() {
		return (List<Rayon>) rayonRepository.findAll();
	}

	@Override
	public Rayon addRayon(Rayon r) {
		return rayonRepository.save(r);
	}

	@Override
	public void deleteRayon(Long id) {
		rayonRepository.deleteById(id);
		
	}

	@Override
	public Rayon updateRayon(Rayon c) {
		// TODO Auto-generated method stub
		return rayonRepository.save(c);
	}

	@Override
	public Rayon retrieveRayon(Long id) {
		// TODO Auto-generated method stub
		return rayonRepository.findById(id).orElse(null);
	}

	
}
