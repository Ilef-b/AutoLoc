package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public List<Paiement> getAll() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement getById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paiement introuvable : id = " + id));
    }

    @Override
    public Paiement create(Paiement paiement) {
        paiement.setIdPaiement(null);
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Long id, Paiement paiement) {
        Paiement existant = getById(id);
        existant.setMontant(paiement.getMontant());
        existant.setDatePaiement(paiement.getDatePaiement());
        existant.setModePaiement(paiement.getModePaiement());
        return paiementRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new EntityNotFoundException("Paiement introuvable : id = " + id);
        }
        paiementRepository.deleteById(id);
    }
}