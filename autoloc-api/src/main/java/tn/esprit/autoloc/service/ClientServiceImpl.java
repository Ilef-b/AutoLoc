package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable : id = " + id));
    }

    @Override
    public Client create(Client client) {
        client.setIdClient(null);
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client existant = getById(id);
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        return clientRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client introuvable : id = " + id);
        }
        clientRepository.deleteById(id);
    }
}