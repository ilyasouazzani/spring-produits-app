package ma.enset.springproduitsapp.repositories;

import ma.enset.springproduitsapp.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, String> {
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByMedecinId(Long medecinId);
}
