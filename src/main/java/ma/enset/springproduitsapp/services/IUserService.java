package ma.enset.springproduitsapp.services;

import ma.enset.springproduitsapp.entities.AppRole;
import ma.enset.springproduitsapp.entities.AppUser;

public interface IUserService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    void removeRoleFromUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
}
