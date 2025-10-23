package com.example.altenshop_backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//composant métier géré par Spring injectée au besoin.
@Service
public class ProductService {

    private final String filePath = "products.json";
    private List<Product> products = new ArrayList<>();
    //bibliothèque Jackson qui convertit les objets Java en JSON et inversement.
    private final ObjectMapper objectMapper = new ObjectMapper();

    //automatically called after class instanciaiton 
    @PostConstruct
    public void init() {
        loadProducts();
    }
    //synchronized avoid simultaneous uncrolled changed over product list
    private synchronized void loadProducts() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                products = objectMapper.readValue(file, new TypeReference<List<Product>>(){});
            } else {
                products = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            products = new ArrayList<>(); // charger liste vide en cas d'erreur
        }
    }

    private synchronized void saveProducts() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Product> getAll() {
        return new ArrayList<>(products);
    }

    public synchronized Optional<Product> getById(long id) {
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }

    public synchronized void addProduct(Product product) {
        products.add(product);
        //automatically set the product id
        product.setId(products.stream().mapToLong(Product::getId).max().orElse(0L) + 1);

        saveProducts();
    }

    public synchronized boolean updateProduct(long id, Product product) {
        System.out.println("produit a update : " + product);
        Optional<Product> existing = getById(id);
        if (existing.isPresent()) {
            Product p = existing.get();
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setQuantity(product.getQuantity());
            saveProducts();
            return true;
        }
        return false;
    }

    public synchronized boolean deleteProduct(long id) {
        Optional<Product> existing = getById(id);
        if (existing.isPresent()) {
            products.remove(existing.get());
            saveProducts();
            return true;
        }
        return false;
    }

    public Product updatePartially(Long id, Product partialProduct) {
        Optional<Product> existingOpt = getById(id);
        if (existingOpt.isEmpty()) {
            return null; // unfound
        }
        Product existing = existingOpt.get();

        if (partialProduct.getName() != null && !partialProduct.getName().isEmpty()) {
            existing.setName(partialProduct.getName());
        }
        if (partialProduct.getDescription() != null && !partialProduct.getDescription().isEmpty()) {
            existing.setDescription(partialProduct.getDescription());
        }
        if (partialProduct.getImage() != null && !partialProduct.getImage().isEmpty()) {
            existing.setImage(partialProduct.getImage());
        }
        if (partialProduct.getCategory() != null && !partialProduct.getCategory().isEmpty()) {
            existing.setCategory(partialProduct.getCategory());
        }
        if (partialProduct.getPrice() != 0) {
            existing.setPrice(partialProduct.getPrice());
        }
        if (partialProduct.getQuantity() != 0) {
            existing.setQuantity(partialProduct.getQuantity());
        }
     
        saveProducts();

        return existing;
    }
}
