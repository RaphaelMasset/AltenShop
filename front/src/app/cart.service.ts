import { Injectable, signal } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';

@Injectable({ providedIn: 'root' })
export class CartService {
  private _cart = signal<Product[]>([]);

  // Accès en lecture seule
  get cart() {
    return this._cart.asReadonly();
  }

  // Ajouter un produit
  addToCart(product: Product) {
    const current = this._cart();
    const existing = current.find(p => p.id === product.id);
    if (existing) {
      existing.quantity += 1;
      this._cart.set([...current]);
    } else {
      this._cart.set([...current, { ...product, quantity: 1 }]);
    }
  }

  // Supprimer un produit
  removeFromCart(product: Product) {
    const current = this._cart();
    const existing = current.find(p => p.id === product.id);
    if (existing) {
      existing.quantity -= 1;
      if (existing.quantity <= 0) {
        this._cart.set(current.filter(p => p.id !== product.id));
      } else {
        this._cart.set([...current]);
      }
    }
  }
}
