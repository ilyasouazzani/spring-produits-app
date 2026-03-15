# 🌿 Spring Produits App

Application Web JEE complète basée sur **Spring Boot**, **Spring Data JPA**, **Hibernate**, **Thymeleaf** et **Spring Security**.

---

## 🏗️ Architecture

```
Application
    └── Spring Data (Repository Layer)
            └── JPA (EntityManager, EntityManagerFactory)
                    ├── Hibernate ← (ORM Provider)
                    ├── Toplink
                    └── EclipseLink
                            └── JDBC
                                    └── SGBD (H2 / MySQL)
```

## 📦 Fonctionnalités

### Produits
- ✅ Liste paginée avec recherche
- ✅ Ajout avec validation du formulaire
- ✅ Modification et mise à jour
- ✅ Suppression (admin seulement)

### Patients
- ✅ Liste paginée avec recherche
- ✅ CRUD complet
- ✅ Score de santé avec barre de progression
- ✅ Statut malade/en bonne santé

### Médecins
- ✅ Liste avec cards Bootstrap
- ✅ CRUD complet
- ✅ Affichage par spécialité

### Sécurité
- ✅ Authentification par formulaire (page de login custom)
- ✅ Gestion des rôles (USER, ADMIN)
- ✅ Protection par rôle des endpoints (@PreAuthorize)
- ✅ Remember-me
- ✅ Gestion des utilisateurs (admin)

---

## 🛠️ Stack Technique

| Technologie       | Version  | Rôle                   |
|-------------------|----------|------------------------|
| Spring Boot       | 3.2.3    | Framework principal    |
| Spring Data JPA   | -        | Couche DAO             |
| Hibernate         | -        | ORM Provider           |
| Thymeleaf         | -        | Template engine        |
| Spring Security   | 6.x      | Authentification       |
| Bootstrap         | 5.3.2    | UI Framework           |
| H2 Database       | -        | Base de données (dev)  |
| MySQL             | -        | Base de données (prod) |
| Lombok            | -        | Réduction boilerplate  |
| Java              | 17       | Langage                |

---

## 🚀 Lancement

### Prérequis
- Java 17+
- Maven 3.8+

### Avec H2 (par défaut)
```bash
mvn spring-boot:run
```
→ Accéder à http://localhost:8080

### Avec MySQL
1. Créer la base de données MySQL :
```sql
CREATE DATABASE produits_db;
```
2. Modifier `application.properties` :
```properties
# Décommenter les lignes MySQL et commenter H2
spring.datasource.url=jdbc:mysql://localhost:3306/produits_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---

## 👤 Comptes utilisateurs de test

| Utilisateur | Mot de passe | Rôle        |
|-------------|-------------|-------------|
| user1       | 1234        | USER        |
| user2       | 1234        | USER        |
| admin       | 1234        | USER, ADMIN |

---

## 📁 Structure du projet

```
src/
├── main/
│   ├── java/ma/enset/springproduitsapp/
│   │   ├── entities/           ← Entités JPA
│   │   │   ├── Product.java
│   │   │   ├── Patient.java
│   │   │   ├── Medecin.java
│   │   │   ├── RendezVous.java
│   │   │   ├── Consultation.java
│   │   │   ├── AppUser.java
│   │   │   └── AppRole.java
│   │   ├── repositories/       ← Spring Data JPA
│   │   ├── services/           ← Couche service
│   │   ├── security/           ← Spring Security
│   │   └── web/                ← Contrôleurs MVC
│   └── resources/
│       ├── application.properties
│       └── templates/          ← Thymeleaf
│           ├── layout/template.html
│           ├── products/
│           ├── patients/
│           ├── medecins/
│           ├── admin/
│           ├── login.html
│           └── notAuthorized.html
└── test/
```

---

## 🔐 Sécurité

La configuration Spring Security (Spring Security 6) utilise :
- **DaoAuthenticationProvider** avec UserDetailsService basé sur la base de données
- **BCryptPasswordEncoder** pour le hachage des mots de passe
- **SecurityFilterChain** (pas de WebSecurityConfigurerAdapter deprecated)
- **@PreAuthorize** pour le contrôle d'accès au niveau méthode

---

## 📺 Vidéos de référence

- https://www.youtube.com/watch?v=FHy7raIldgg
- https://www.youtube.com/watch?v=Kfv_7m8INlU
- https://www.youtube.com/watch?v=s6p2dE3qrsU

---

## 📝 Auteur

Projet réalisé dans le cadre d'un TP JEE - ENSET Mohammedia
