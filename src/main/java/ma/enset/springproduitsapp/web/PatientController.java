package ma.enset.springproduitsapp.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.springproduitsapp.entities.Patient;
import ma.enset.springproduitsapp.repositories.PatientRepository;
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
public class PatientController {

    private final PatientRepository patientRepository;

    @GetMapping("/patients")
    public String listPatients(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<Patient> patients = patientRepository.findByNomContainsIgnoreCase(keyword, PageRequest.of(page, size));
        model.addAttribute("patients", patients.getContent());
        model.addAttribute("pages", new int[patients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalItems", patients.getTotalElements());
        return "patients/list";
    }

    @GetMapping("/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("mode", "add");
        return "patients/form";
    }

    @PostMapping("/savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(
            @Valid @ModelAttribute Patient patient,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", patient.getId() == null ? "add" : "edit");
            return "patients/form";
        }
        patientRepository.save(patient);
        redirectAttributes.addFlashAttribute("success",
            patient.getId() == null ? "Patient ajouté avec succès!" : "Patient mis à jour avec succès!");
        return "redirect:/patients";
    }

    @GetMapping("/editPatient/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable: " + id));
        model.addAttribute("patient", patient);
        model.addAttribute("mode", "edit");
        return "patients/form";
    }

    @GetMapping("/deletePatient/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePatient(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            RedirectAttributes redirectAttributes) {
        patientRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Patient supprimé avec succès!");
        return "redirect:/patients?page=" + page + "&keyword=" + keyword;
    }
}
