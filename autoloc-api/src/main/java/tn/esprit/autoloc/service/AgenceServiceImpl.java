package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public List<Agence> getAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence getById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : id = " + id));
    }

    @Override
    public Agence create(Agence agence) {
        agence.setIdAgence(null);
        return agenceRepository.save(agence);
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existante = getById(id);
        existante.setNom(agence.getNom());
        existante.setVille(agence.getVille());
        existante.setAdresse(agence.getAdresse());
        existante.setTelephone(agence.getTelephone());
        return agenceRepository.save(existante);
    }

    @Override
    public void delete(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable : id = " + id);
        }
        agenceRepository.deleteById(id);
    }
}