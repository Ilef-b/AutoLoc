package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface IContratService {

    List<Contrat> getAll();

    Contrat getById(Long id);

    Contrat create(Contrat contrat);

    Contrat update(Long id, Contrat contrat);

    void delete(Long id);
}