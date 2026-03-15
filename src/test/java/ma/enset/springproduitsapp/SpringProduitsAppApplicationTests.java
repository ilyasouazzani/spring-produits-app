package ma.enset.springproduitsapp;

import ma.enset.springproduitsapp.entities.Product;
import ma.enset.springproduitsapp.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringProduitsAppApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
        System.out.println("✅ Contexte Spring chargé avec succès");
    }

    @Test
    void testProductRepository() {
        // Save a product
        Product product = Product.builder()
                .name("Test Product")
                .price(999.99)
                .quantity(10)
                .description("Test description")
                .build();
        Product saved = productRepository.save(product);
        assertThat(saved.getId()).isNotNull();
        System.out.println("✅ Produit sauvegardé: " + saved);

        // Find by name
        Page<Product> found = productRepository.findByNameContainsIgnoreCase("test", PageRequest.of(0, 5));
        assertThat(found.getContent()).isNotEmpty();
        System.out.println("✅ Recherche par nom: " + found.getContent().size() + " résultat(s)");

        // Delete
        productRepository.deleteById(saved.getId());
        assertThat(productRepository.findById(saved.getId())).isEmpty();
        System.out.println("✅ Produit supprimé avec succès");
    }

    @Test
    void testFindAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("✅ Total produits en base: " + products.size());
        products.forEach(p -> System.out.println("  - " + p.getName() + " : " + p.getPrice() + " MAD"));
    }
}
