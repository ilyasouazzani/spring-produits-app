package ma.enset.springproduitsapp.repositories;

import ma.enset.springproduitsapp.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findByNomContainsIgnoreCase(String keyword, Pageable pageable);

    Page<Patient> findByNomContainsIgnoreCaseOrPrenomContainsIgnoreCase(
        String nom, String prenom, Pageable pageable);
}
