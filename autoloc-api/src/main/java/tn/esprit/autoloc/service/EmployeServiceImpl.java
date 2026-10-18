package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    public List<Employe> getAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe getById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe introuvable : id = " + id));
    }

    @Override
    public Employe create(Employe employe) {
        employe.setIdEmploye(null);
        return employeRepository.save(employe);
    }

    @Override
    public Employe update(Long id, Employe employe) {
        Employe existant = getById(id);
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());
        return employeRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employe introuvable : id = " + id);
        }
        employeRepository.deleteById(id);
    }
}