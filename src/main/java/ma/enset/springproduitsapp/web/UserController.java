package ma.enset.springproduitsapp.web;

import lombok.AllArgsConstructor;
import ma.enset.springproduitsapp.entities.AppRole;
import ma.enset.springproduitsapp.entities.AppUser;
import ma.enset.springproduitsapp.repositories.AppRoleRepository;
import ma.enset.springproduitsapp.repositories.AppUserRepository;
import ma.enset.springproduitsapp.services.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final IUserService userService;

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        List<AppUser> users = appUserRepository.findAll();
        List<AppRole> roles = appRoleRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("newUser", new AppUser());
        return "admin/users";
    }

    @PostMapping("/admin/addUser")
    public String addUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {
        try {
            AppUser user = new AppUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userService.addNewUser(user);
            redirectAttributes.addFlashAttribute("success", "Utilisateur créé avec succès!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/addRoleToUser")
    public String addRoleToUser(
            @RequestParam String username,
            @RequestParam String roleName,
            RedirectAttributes redirectAttributes) {
        try {
            userService.addRoleToUser(username, roleName);
            redirectAttributes.addFlashAttribute("success", "Rôle assigné avec succès!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/removeRoleFromUser")
    public String removeRoleFromUser(
            @RequestParam String username,
            @RequestParam String roleName,
            RedirectAttributes redirectAttributes) {
        try {
            userService.removeRoleFromUser(username, roleName);
            redirectAttributes.addFlashAttribute("success", "Rôle retiré avec succès!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/deleteUser/{userId}")
    public String deleteUser(@PathVariable String userId, RedirectAttributes redirectAttributes) {
        appUserRepository.deleteById(userId);
        redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès!");
        return "redirect:/admin/users";
    }
}
