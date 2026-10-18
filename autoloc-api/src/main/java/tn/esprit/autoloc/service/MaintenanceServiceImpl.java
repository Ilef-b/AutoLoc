package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public List<Maintenance> getAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance getById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance introuvable : id = " + id));
    }

    @Override
    public Maintenance create(Maintenance maintenance) {
        maintenance.setIdMaintenance(null);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        Maintenance existante = getById(id);
        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setDescription(maintenance.getDescription());
        return maintenanceRepository.save(existante);
    }

    @Override
    public void delete(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Maintenance introuvable : id = " + id);
        }
        maintenanceRepository.deleteById(id);
    }
}