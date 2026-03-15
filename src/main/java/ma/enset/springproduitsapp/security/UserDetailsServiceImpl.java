package ma.enset.springproduitsapp.security;

import lombok.AllArgsConstructor;
import ma.enset.springproduitsapp.entities.AppUser;
import ma.enset.springproduitsapp.services.IUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userService.loadUserByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable: " + username);
        }

        String[] roles = appUser.getRoles().stream()
                .map(r -> r.getRoleName())
                .toArray(String[]::new);

        return User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();
    }
}
