package ma.enset.springproduitsapp;

import ma.enset.springproduitsapp.entities.*;
import ma.enset.springproduitsapp.repositories.*;
import ma.enset.springproduitsapp.services.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringProduitsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProduitsAppApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(
            ProductRepository productRepository,
            PatientRepository patientRepository,
            MedecinRepository medecinRepository,
            AppRoleRepository appRoleRepository,
            AppUserRepository appUserRepository,
            IUserService userService) {
        return args -> {
            // ========== PRODUCTS ==========
            Stream.of(
                Product.builder().name("Laptop Dell XPS").price(12999.99).quantity(15).description("Laptop haute performance 16 pouces").build(),
                Product.builder().name("iPhone 15 Pro").price(9999.00).quantity(30).description("Smartphone Apple dernier modèle").build(),
                Product.builder().name("Samsung 4K TV 55\"").price(6499.50).quantity(8).description("Téléviseur QLED 4K 55 pouces").build(),
                Product.builder().name("AirPods Pro").price(1799.00).quantity(50).description("Écouteurs sans fil avec ANC").build(),
                Product.builder().name("MacBook Air M2").price(14999.00).quantity(20).description("Laptop Apple puce M2").build(),
                Product.builder().name("iPad Air").price(7299.00).quantity(25).description("Tablette Apple 10.9 pouces").build(),
                Product.builder().name("Clavier Mécanique").price(899.00).quantity(40).description("Clavier gaming RGB switches Cherry").build(),
                Product.builder().name("Souris Logitech MX").price(599.00).quantity(60).description("Souris ergonomique sans fil").build(),
                Product.builder().name("Moniteur LG 27\"").price(3299.00).quantity(12).description("Moniteur IPS 4K 27 pouces").build(),
                Product.builder().name("SSD Samsung 1TB").price(649.00).quantity(100).description("Disque SSD NVMe M.2 1TB").build()
            ).forEach(productRepository::save);

            // ========== PATIENTS ==========
            Stream.of(
                Patient.builder().nom("Alami").prenom("Mohamed").dateNaissance(new Date()).malade(false).score(85).email("m.alami@email.com").build(),
                Patient.builder().nom("Bennani").prenom("Fatima").dateNaissance(new Date()).malade(true).score(45).email("f.bennani@email.com").build(),
                Patient.builder().nom("Chraibi").prenom("Ahmed").dateNaissance(new Date()).malade(false).score(92).email("a.chraibi@email.com").build(),
                Patient.builder().nom("Daoudi").prenom("Nadia").dateNaissance(new Date()).malade(true).score(60).email("n.daoudi@email.com").build(),
                Patient.builder().nom("El Fassi").prenom("Youssef").dateNaissance(new Date()).malade(false).score(78).email("y.elfassi@email.com").build(),
                Patient.builder().nom("Filali").prenom("Sara").dateNaissance(new Date()).malade(false).score(95).email("s.filali@email.com").build(),
                Patient.builder().nom("Ghali").prenom("Khalid").dateNaissance(new Date()).malade(true).score(30).email("k.ghali@email.com").build(),
                Patient.builder().nom("Hassani").prenom("Laila").dateNaissance(new Date()).malade(false).score(88).email("l.hassani@email.com").build()
            ).forEach(patientRepository::save);

            // ========== MEDECINS ==========
            Stream.of(
                Medecin.builder().nom("Dr. Tahir").email("tahir@hopital.ma").specialite("Cardiologie").build(),
                Medecin.builder().nom("Dr. Mansouri").email("mansouri@hopital.ma").specialite("Neurologie").build(),
                Medecin.builder().nom("Dr. Berrada").email("berrada@hopital.ma").specialite("Pédiatrie").build(),
                Medecin.builder().nom("Dr. Saidi").email("saidi@hopital.ma").specialite("Dermatologie").build(),
                Medecin.builder().nom("Dr. Kohen").email("kohen@hopital.ma").specialite("Ophtalmologie").build()
            ).forEach(medecinRepository::save);

            // ========== ROLES ==========
            if (appRoleRepository.findByRoleName("USER") == null) {
                userService.addNewRole(AppRole.builder().roleName("USER").build());
            }
            if (appRoleRepository.findByRoleName("ADMIN") == null) {
                userService.addNewRole(AppRole.builder().roleName("ADMIN").build());
            }

            // ========== USERS ==========
            if (!appUserRepository.existsByUsername("user1")) {
                AppUser user1 = new AppUser();
                user1.setUsername("user1");
                user1.setPassword("1234");
                user1.setEmail("user1@email.com");
                userService.addNewUser(user1);
                userService.addRoleToUser("user1", "USER");
            }

            if (!appUserRepository.existsByUsername("user2")) {
                AppUser user2 = new AppUser();
                user2.setUsername("user2");
                user2.setPassword("1234");
                user2.setEmail("user2@email.com");
                userService.addNewUser(user2);
                userService.addRoleToUser("user2", "USER");
            }

            if (!appUserRepository.existsByUsername("admin")) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword("1234");
                admin.setEmail("admin@email.com");
                userService.addNewUser(admin);
                userService.addRoleToUser("admin", "USER");
                userService.addRoleToUser("admin", "ADMIN");
            }

            System.out.println("✅ Données d'initialisation chargées avec succès!");
        };
    }
}
