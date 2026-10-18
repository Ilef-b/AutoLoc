package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    List<Vehicule> getAll();

    Vehicule getById(Long id);

    Vehicule create(Vehicule vehicule);

    Vehicule update(Long id, Vehicule vehicule);

    void delete(Long id);
}