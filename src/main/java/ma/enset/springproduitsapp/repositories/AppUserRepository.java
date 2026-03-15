package ma.enset.springproduitsapp.repositories;

import ma.enset.springproduitsapp.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
    boolean existsByUsername(String username);
}
