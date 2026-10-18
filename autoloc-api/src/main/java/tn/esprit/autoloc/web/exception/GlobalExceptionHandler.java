package tn.esprit.autoloc.web.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Une ressource demandee n'existe pas -> 404 Not Found (au lieu de 500)
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleNotFound(EntityNotFoundException ex) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        probleme.setTitle("Ressource introuvable");
        return probleme;
    }

    // Une contrainte de la base est violee (doublon sur une colonne unique, cle etrangere...) -> 409 Conflict
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleConflict(DataIntegrityViolationException ex) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Operation refusee : une donnee unique existe deja (email, numero de permis, immatriculation...) "
                        + "ou la ressource est encore liee a d'autres donnees.");
        probleme.setTitle("Conflit de donnees");
        return probleme;
    }
}