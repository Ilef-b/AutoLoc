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
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@Tag(name = "Agences", description = "Gestion des agences AutoLoc (CRUD)")
@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister toutes les agences",
            description = "Retourne la liste complete des agences enregistrees.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des agences retournee")
    })
    @GetMapping
    public List<Agence> getAll() {
        return agenceService.getAll();
    }

    @Operation(summary = "Consulter une agence",
            description = "Retourne l'agence correspondant a l'identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvee"),
            @ApiResponse(responseCode = "404", description = "Aucune agence avec cet identifiant",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public Agence getById(@PathVariable Long id) {
        return agenceService.getById(id);
    }

    @Operation(summary = "Creer une agence",
            description = "Enregistre une nouvelle agence. L'identifiant est genere automatiquement.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence creee"),
            @ApiResponse(responseCode = "409", description = "Conflit avec une donnee existante",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @Operation(summary = "Modifier une agence",
            description = "Met a jour le nom, la ville, l'adresse et le telephone d'une agence existante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence modifiee"),
            @ApiResponse(responseCode = "404", description = "Aucune agence avec cet identifiant",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @Operation(summary = "Supprimer une agence",
            description = "Supprime une agence. Refuse si des employes ou vehicules y sont encore rattaches.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agence supprimee"),
            @ApiResponse(responseCode = "404", description = "Aucune agence avec cet identifiant",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Agence encore liee a des employes ou vehicules",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        agenceService.delete(id);
    }
}