package tn.esprit.autoloc.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Reservation;
public interface IReservationRepositor extends JpaRepository<Reservation, Long>{
}
