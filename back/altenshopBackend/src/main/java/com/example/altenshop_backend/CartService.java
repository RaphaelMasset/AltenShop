package com.example.altenshop_backend;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    // Map clé=email utilisateur, valeur=liste d'articles dans le panier
    private final Map<String, List<CartItem>> carts = new HashMap<>();

    // Ajouter un produit au panier d'un utilisateur
    public synchronized void addToCart(String userEmail, CartItem item) {
        //get the cartlist given the email adress of the user or return an empty new one
        List<CartItem> userCart = carts.computeIfAbsent(userEmail, k -> new ArrayList<>());
        //increase quantity if already in teh cart
        for (CartItem ci : userCart) {
            if (ci.getProductId() == item.getProductId()) {
                ci.setQuantity(ci.getQuantity() + item.getQuantity());
                return;
            }
        }
        //if not already in cart just add it
        userCart.add(item);
    }

    public synchronized List<CartItem> getCart(String userEmail) {
        return new ArrayList<>(carts.getOrDefault(userEmail, new ArrayList<>()));
    }

    public synchronized boolean removeFromCart(String userEmail, long productId) {
        List<CartItem> userCart = carts.get(userEmail);
        if (userCart == null) return false;
        return userCart.removeIf(ci -> ci.getProductId() == productId);
    }

    public synchronized void clearCart(String userEmail) {
        carts.remove(userEmail);
    }
}
