package ma.enset.springproduitsapp.services;

import lombok.AllArgsConstructor;
import ma.enset.springproduitsapp.entities.AppRole;
import ma.enset.springproduitsapp.entities.AppUser;
import ma.enset.springproduitsapp.repositories.AppRoleRepository;
import ma.enset.springproduitsapp.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(AppUser appUser) {
        if (appUserRepository.existsByUsername(appUser.getUsername())) {
            throw new RuntimeException("L'utilisateur " + appUser.getUsername() + " existe déjà");
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) throw new RuntimeException("Utilisateur introuvable: " + username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole == null) throw new RuntimeException("Rôle introuvable: " + roleName);
        appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) throw new RuntimeException("Utilisateur introuvable: " + username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole == null) throw new RuntimeException("Rôle introuvable: " + roleName);
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
