package ma.enset.springproduitsapp.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.springproduitsapp.entities.Medecin;
import ma.enset.springproduitsapp.repositories.MedecinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class MedecinController {

    private final MedecinRepository medecinRepository;

    @GetMapping("/medecins")
    public String listMedecins(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model) {
        Page<Medecin> medecins = medecinRepository.findByNomContainsIgnoreCase(keyword, PageRequest.of(page, size));
        model.addAttribute("medecins", medecins.getContent());
        model.addAttribute("pages", new int[medecins.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalItems", medecins.getTotalElements());
        return "medecins/list";
    }

    @GetMapping("/formMedecin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formMedecin(Model model) {
        model.addAttribute("medecin", new Medecin());
        model.addAttribute("mode", "add");
        return "medecins/form";
    }

    @PostMapping("/saveMedecin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveMedecin(
            @Valid @ModelAttribute Medecin medecin,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", medecin.getId() == null ? "add" : "edit");
            return "medecins/form";
        }
        medecinRepository.save(medecin);
        redirectAttributes.addFlashAttribute("success",
            medecin.getId() == null ? "Médecin ajouté avec succès!" : "Médecin mis à jour avec succès!");
        return "redirect:/medecins";
    }

    @GetMapping("/editMedecin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editMedecin(@PathVariable Long id, Model model) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable: " + id));
        model.addAttribute("medecin", medecin);
        model.addAttribute("mode", "edit");
        return "medecins/form";
    }

    @GetMapping("/deleteMedecin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteMedecin(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            RedirectAttributes redirectAttributes) {
        medecinRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Médecin supprimé avec succès!");
        return "redirect:/medecins?page=" + page + "&keyword=" + keyword;
    }
}
