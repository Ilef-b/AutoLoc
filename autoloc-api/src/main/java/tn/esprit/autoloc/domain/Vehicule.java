package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    @Column(nullable = false, length = 50)
    private String marque;

    @Column(nullable = false, length = 50)
    private String modele;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategorieVehicule categorie;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutVehicule statut;

    // N Vehicule -> 1 Agence : la cle etrangere id_agence est dans la table vehicule
    // (ignoree dans le JSON : l'affectation a une agence sera une operation dediee, Seance 8)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agence")
    private Agence agence;

    // 1 Vehicule -> N Maintenance : enregistrer un vehicule enregistre aussi ses nouvelles maintenances
    @JsonIgnore
    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.PERSIST)
    private List<Maintenance> maintenances = new ArrayList<>();

    // N Vehicule <-> N Equipement : cote proprietaire, cree la table de jointure vehicule_equipement
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "id_vehicule"),
            inverseJoinColumns = @JoinColumn(name = "id_equipement")
    )
    private Set<Equipement> equipements = new HashSet<>();

    // 1 Vehicule -> N Reservation : cote inverse, pas de cascade
    @JsonIgnore
    @OneToMany(mappedBy = "vehicule")
    private List<Reservation> reservations = new ArrayList<>();
}