package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    public List<Equipement> getAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement getById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipement introuvable : id = " + id));
    }

    @Override
    public Equipement create(Equipement equipement) {
        equipement.setIdEquipement(null);
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        Equipement existant = getById(id);
        existant.setLibelle(equipement.getLibelle());
        return equipementRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipement introuvable : id = " + id);
        }
        equipementRepository.deleteById(id);
    }
}