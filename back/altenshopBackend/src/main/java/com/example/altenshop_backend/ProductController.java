package com.example.altenshop_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//marque class as a REST controller
@RestController
@RequestMapping("/api/products") //define api path, all method inside will repond a path starting with it
public class ProductController {
//class managing HTTP requests  (GET, POST, PUT, DELETE).
//use Prduct service to manage data
    private final ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //paths defined with @GetMapping, @PostMapping
    @GetMapping
    public List<Product> getAllProducts() {
        System.out.println(productService.getById(1000).toString());
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        return productService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<String> addProduct(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody Product product) {

        // ton code pour vérifier l'admin
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        if (!"admin@admin.com".equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé");
        }

        productService.addProduct(product);
        return ResponseEntity.ok("Produit ajouté");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable long id, 
        @RequestBody Product product) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);
        System.out.println("updateProduct method called in ProductController: ");

        if (!"admin@admin.com".equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (productService.updateProduct(id, product)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable long id) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        if (!"admin@admin.com".equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchUpdateProduct(
        @RequestHeader("Authorization") String authHeader,
   
        @PathVariable long id,
        @RequestBody Product partialProduct) {

       /*  String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        if (!"admin@admin.com".equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }*/

        Product updated = productService.updatePartially(id, partialProduct);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }
}
