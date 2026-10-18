package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public List<Vehicule> getAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule getById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule introuvable : id = " + id));
    }

    @Override
    public Vehicule create(Vehicule vehicule) {
        vehicule.setIdVehicule(null);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = getById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicule introuvable : id = " + id);
        }
        vehiculeRepository.deleteById(id);
    }
}