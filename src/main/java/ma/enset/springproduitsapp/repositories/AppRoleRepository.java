package ma.enset.springproduitsapp.repositories;

import ma.enset.springproduitsapp.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, String> {
    AppRole findByRoleName(String roleName);
}
