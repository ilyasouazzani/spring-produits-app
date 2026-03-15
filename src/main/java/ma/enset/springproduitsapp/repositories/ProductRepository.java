package ma.enset.springproduitsapp.repositories;

import ma.enset.springproduitsapp.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainsIgnoreCase(String keyword, Pageable pageable);

    Page<Product> findByNameContainsIgnoreCaseAndPriceBetween(
        String keyword, Double minPrice, Double maxPrice, Pageable pageable);
}
