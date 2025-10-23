package com.example.altenshop_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    //gestion des tokens JWT
    @Autowired
    private JwtUtil jwtUtil;

    //manage post request /api/cart/add
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CartItem item) {
        //isolate the token by taking out the bearer part of the header
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        cartService.addToCart(email, item);
        return ResponseEntity.ok("Produit ajouté au panier");
    }

    // Récupérer le contenu du panier de l'utilisateur connecté
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        List<CartItem> cart = cartService.getCart(email);
        return ResponseEntity.ok(cart);
    }

    // Supprimer un produit du panier
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable long productId) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        boolean removed = cartService.removeFromCart(email, productId);
        if (removed) {
            return ResponseEntity.ok("Produit retiré du panier");
        } else {
            return ResponseEntity.status(404).body("Produit non trouvé dans le panier");
        }
    }
}
