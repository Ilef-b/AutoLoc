package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

public interface IPaiementService {

    List<Paiement> getAll();

    Paiement getById(Long id);

    Paiement create(Paiement paiement);

    Paiement update(Long id, Paiement paiement);

    void delete(Long id);
}