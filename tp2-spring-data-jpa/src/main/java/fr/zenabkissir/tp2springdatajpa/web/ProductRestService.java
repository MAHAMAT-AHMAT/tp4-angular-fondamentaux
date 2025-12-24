package fr.zenabkissir.tp2springdatajpa.web;

import fr.zenabkissir.tp2springdatajpa.entities.Product;
import fr.zenabkissir.tp2springdatajpa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> products() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return product;
    }
    @DeleteMapping("/products/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        productRepository.deleteById(id);
    }
}
