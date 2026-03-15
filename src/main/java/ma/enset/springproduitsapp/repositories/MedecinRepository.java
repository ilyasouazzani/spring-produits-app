package ma.enset.springproduitsapp.repositories;

import ma.enset.springproduitsapp.entities.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Page<Medecin> findByNomContainsIgnoreCase(String keyword, Pageable pageable);
    Medecin findByEmail(String email);
}
