package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Tag(name = "Vehicules", description = "Gestion de la flotte de vehicules AutoLoc (CRUD)")
@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister tous les vehicules",
            description = "Retourne la liste complete des vehicules de la flotte.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des vehicules retournee")
    })
    @GetMapping
    public List<Vehicule> getAll() {
        return vehiculeService.getAll();
    }

    @Operation(summary = "Consulter un vehicule",
            description = "Retourne le vehicule correspondant a l'identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule trouve"),
            @ApiResponse(responseCode = "404", description = "Aucun vehicule avec cet identifiant",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public Vehicule getById(@PathVariable Long id) {
        return vehiculeService.getById(id);
    }

    @Operation(summary = "Ajouter un vehicule",
            description = "Ajoute un vehicule a la flotte. L'immatriculation doit etre unique. "
                    + "Categorie : CITADINE, BERLINE, SUV, UTILITAIRE. Statut : DISPONIBLE, LOUE, MAINTENANCE.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehicule ajoute"),
            @ApiResponse(responseCode = "409", description = "Immatriculation deja utilisee",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @Operation(summary = "Modifier un vehicule",
            description = "Met a jour les informations d'un vehicule existant (marque, modele, tarif, statut...).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule modifie"),
            @ApiResponse(responseCode = "404", description = "Aucun vehicule avec cet identifiant",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Immatriculation deja utilisee par un autre vehicule",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un vehicule",
            description = "Retire un vehicule de la flotte. Refuse s'il a encore des reservations.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehicule supprime"),
            @ApiResponse(responseCode = "404", description = "Aucun vehicule avec cet identifiant",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Vehicule encore lie a des reservations",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vehiculeService.delete(id);
    }
}