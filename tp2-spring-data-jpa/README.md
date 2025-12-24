# API de gestion des produits - Application Spring Boot

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-Database-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build%20Tool-red.svg)](https://maven.apache.org/)

##  Description

Une application Spring Boot simple qui démontre les concepts fondamentaux du développement d'APIs REST avec Spring Data
JPA. Cette application gère un système de produits avec des opérations CRUD et des requêtes personnalisées.

## Architecture du Projet

```
src/
├── main/
│   ├── java/fr/zenabkissir/tp2springdatajpa/
│   │   ├── entities/
│   │   │   └── Product.java           # Entité JPA
│   │   ├── repositories/
│   │   │   └── ProductRepository.java # Interface de données
│   │   ├── web/
│   │   │   └── ProductRestService.java #  Contrôleur REST
│   │   └── Tp2SpringDataJpaApplication.java # ⚡ Classe principale
│   └── resources/
│       └── application.properties     #  Configuration
└── pom.xml                           # Dépendances Maven
```

## Entité Product

Le fichier `Product.java` définit l'entité principale de l'application :

```java

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
```

### Annotations utilisées :

- `@Entity` : Marque la classe comme une entité JPA
- `@Data` : Génère automatiquement les getters, setters, toString, etc. (Lombok)
- `@NoArgsConstructor` / `@AllArgsConstructor` : Génère les constructeurs (Lombok)
- `@Builder` : Implémente le pattern Builder (Lombok)
- `@Id` : Définit la clé primaire
- `@GeneratedValue` : Auto-incrémentation de l'ID

## Repository - ProductRepository

L'interface `ProductRepository.java` étend `JpaRepository` et fournit des méthodes de requête personnalisées :

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Recherche par nom (contient)
    List<Product> findByNameContains(String mc);

    // Recherche par prix supérieur
    List<Product> findByPriceGreaterThan(double price);

    // Requête JPQL personnalisée pour la recherche
    @Query("select p from Product p where p.name like :x")
    List<Product> search(@Param("x") String mc);

    //  Requête JPQL pour le prix
    @Query("select p from Product p where p.price > :x")
    List<Product> searchByPrice(@Param("x") double price);
}
```

###  Fonctionnalités :

- **Méthodes dérivées** : Spring Data génère automatiquement l'implémentation
- **Requêtes JPQL** : Requêtes personnalisées avec `@Query`
- **Paramètres nommés** : Utilisation de `@Param` pour la sécurité

## Contrôleur REST - ProductRestService

Le fichier `ProductRestService.java` expose les endpoints REST :

```java

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> products() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
```

### Endpoints disponibles :

- `GET /products` : Récupère tous les produits
- `GET /products/{id}` : Récupère un produit par son ID

## Classe Principale - TP2SpingDataJpaAppApplication

La classe principale `TP2SpingDataJpaAppApplication.java` démarre l'application et teste les fonctionnalités :

```java

@SpringBootApplication
public class TP2SpingDataJpaAppApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(TP2SpingDataJpaAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //  Tests des différentes méthodes du repository
        // Affichage de tous les produits
        // Tests de recherche par nom et prix
    }
}
```

### Tests inclus :

-  Affichage de tous les produits
-  Recherche d'un produit par ID
-  Recherche par nom (contient "C")
-  Recherche JPQL avec pattern ("%S%")
-  Recherche par prix supérieur à 1200

##  Configuration - application.properties

Le fichier `application.properties` configure l'application :

```properties
#  Nom de l'application
spring.application.name=TP2SpingDataJpaA
# Port du serveur
server.port=8085
# Configuration MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/product_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
# Configuration Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
# Configuration H2 (commentée)
#spring.datasource.url=jdbc:h2:mem:product-db
#spring.h2.console.enabled=true
```

## Dépendances Maven - pom.xml

Le fichier `pom.xml` définit les dépendances du projet :

```xml

<dependencies>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
   </dependency>

   <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
   </dependency>
   <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
      <scope>runtime</scope>
   </dependency>
   <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
   </dependency>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
   </dependency>
</dependencies>
```

## Installation et Exécution

### Prérequis

-  **Java 17** ou supérieur
- ️ **MySQL** en fonctionnement (localhost:3306)
-  **Maven** pour la gestion des dépendances

### Étapes d'installation

1. **Cloner le repository** :

```bash
git clone https://github.com/MAHAMAT-AHMAT/tp2-spring-data-jpa
cd tp2-spring-data-jpa
```

2. **Configurer la base de données** :
    - Démarrer MySQL
    - La base `product_db` sera créée automatiquement

3. **Compiler et exécuter** :

```bash
mvn clean install
mvn spring-boot:run
```

