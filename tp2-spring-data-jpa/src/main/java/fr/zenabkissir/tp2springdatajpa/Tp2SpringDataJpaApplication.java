package fr.zenabkissir.tp2springdatajpa;

import fr.zenabkissir.tp2springdatajpa.entities.Product;
import fr.zenabkissir.tp2springdatajpa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Tp2SpringDataJpaApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(Tp2SpringDataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            productRepository.save(new Product(null, "Computer", 6500, 12, true));
            productRepository.save(new Product(null, "Printer", 1200, 15, false));
            productRepository.save(new Product(null, "Smartphone", 1400, 20, true));
        }

        List<Product> products = productRepository.findAll();
        products.forEach(p -> {
            System.out.println(p.toString());
        });

        Product product = productRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Table Product vide"));
        System.out.println("****************");
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQuantity());
        System.out.println("****************");

        System.out.println("------------------------");

        List<Product> productList = productRepository.findByNameContains("C");
        productList.forEach(p -> {
            System.out.println(p);
        });

        System.out.println("------------------------");

        List<Product> productList2 = productRepository.search("%S%");
        productList2.forEach(p -> {
            System.out.println(p);
        });

        System.out.println("------------------------");

        List<Product> productList3 = productRepository.searchByPrice(1200);
        productList3.forEach(p -> {
            System.out.println(p);
        });
    }
}
