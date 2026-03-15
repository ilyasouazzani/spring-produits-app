package ma.enset.springproduitsapp.repositories;

import ma.enset.springproduitsapp.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    Consultation findByRendezVousId(String rdvId);
}
