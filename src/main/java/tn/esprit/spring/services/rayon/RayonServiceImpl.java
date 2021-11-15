package tn.esprit.spring.services.rayon;

import java.util.List;

import tn.esprit.spring.entities.Rayon;


public interface RayonServiceImpl {
	
	List<Rayon> retrieveAllRayons();

	Rayon addRayon(Rayon r);

	void deleteRayon(Long id);

	Rayon updateRayon(Rayon c);

	Rayon retrieveRayon(Long id);

}
