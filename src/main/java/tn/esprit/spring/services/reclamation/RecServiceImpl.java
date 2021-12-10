package tn.esprit.spring.services.reclamation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ProduitRepository;
import tn.esprit.spring.repositories.ReclamationRepository;
import tn.esprit.spring.services.produit.*;

@Service
public class RecServiceImpl  implements ReclamationServiceImpl{

	@Autowired
	private ReclamationRepository ReclamationRepository;
	
	@Autowired
	private PrdServiceImpl PrdServiceImpl;

	private Object id;

	private Object most_common_item;
	
	
	@Override
	public List<Reclamation> retrieveAllReclamations() {
		return (List<Reclamation>) ReclamationRepository.findAll();

	}

	@Override
	public Reclamation addReclamation(Reclamation r, Long idProduit) {
		r.setProduit(PrdServiceImpl.retrieveProduit(idProduit));	
	
		return ReclamationRepository.save(r);
	}

	@Override
	public Reclamation retrieveReclamation(Long id) {
		return ReclamationRepository.findById(id).orElse(null);
		}

	@Override
	public void deleteReclamation(Long id) {
		ReclamationRepository.deleteById(id);		
	}

	@Override
	public List<Reclamation> retrieveRecByProduitId(Long idProduit) {
		return ReclamationRepository.getRecByProduitId(idProduit);
	}
	
	
	
	@Override
	public   HashMap<String, Integer> Claimmax ( ) {
		int claim_validee=0;
		int claim_encours=0;
		int nbr_claim =  0;
		
		for ( Reclamation r :ReclamationRepository.findAll()) {
	    	if (r.getStatus().equals("Validée"))
	    		claim_validee=claim_validee+1;
	    	   
	    	else {
	    		 claim_encours=claim_encours+1;
	    	}
	    	}
	    	
	    	
	    	 nbr_claim=claim_validee+claim_encours;
	    	 HashMap<String, Integer> statique  = new HashMap<String, Integer>();
	    	 statique.put("reclamation validée", claim_validee);
	    	 statique.put("reclamation en cours",  claim_encours);
	    	 
	    	 return statique;
		
	}
	
	
	public Produit WorstProduct()
	// Get list Reclamations
	// Get idProduit de chaque reclamation
	// calculer occurence de chaq ID

	{ 
	  
    int max=0;
	Long e = null;
	
	 List<Long> IdProduits= new ArrayList<>();
	for ( Reclamation r:ReclamationRepository.findAll())	 
	{ 
     IdProduits.add(r.getProduit().getIdProduit());
	}
	 System.out.println(IdProduits);
	for(int i=0; i<IdProduits.size();i++)
	{int counter=1;
	for(int j=i+1; j<IdProduits.size(); j++)
	{ if(IdProduits.get(i)==IdProduits.get(j)) 
	{
		counter++;
	}
	}
	if(max<counter) {
		max=counter;
		e=IdProduits.get(i);
		 System.out.println("Le produit d'ID: " + e + ", a le plus grand nombre de réclamations: " + max);

	}
	}
	return PrdServiceImpl.retrieveProduit(e);

	}

	
	
	
}
