package ma.enset.springproduitsapp.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.springproduitsapp.entities.Product;
import ma.enset.springproduitsapp.repositories.ProductRepository;
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
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<Product> products = productRepository.findByNameContainsIgnoreCase(keyword, PageRequest.of(page, size));
        model.addAttribute("products", products.getContent());
        model.addAttribute("pages", new int[products.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalItems", products.getTotalElements());
        return "products/list";
    }

    @GetMapping("/formProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("mode", "add");
        return "products/form";
    }

    @PostMapping("/saveProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveProduct(
            @Valid @ModelAttribute Product product,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", product.getId() == null ? "add" : "edit");
            return "products/form";
        }
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("success",
            product.getId() == null ? "Produit ajouté avec succès!" : "Produit mis à jour avec succès!");
        return "redirect:/products";
    }

    @GetMapping("/editProduct/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable: " + id));
        model.addAttribute("product", product);
        model.addAttribute("mode", "edit");
        return "products/form";
    }

    @GetMapping("/deleteProduct/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Produit supprimé avec succès!");
        return "redirect:/products?page=" + page + "&keyword=" + keyword;
    }
}
