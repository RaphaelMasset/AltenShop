import { Component, OnInit, inject } from '@angular/core';
import { CartService } from 'app/cart.service';
import { Product } from 'app/products/data-access/product.model';
import { DataViewModule } from 'primeng/dataview';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, DataViewModule, CardModule, ButtonModule],
  templateUrl: "./cart.component.html",
  styleUrls: ["./cart.component.css"]
})
export class CartComponent implements OnInit {
  private cartService = inject(CartService);

  // signal pour lire le panier
  cart = this.cartService.cart;

  ngOnInit() {}

  removeFromCart(product: Product) {
    this.cartService.removeFromCart(product);
  }
}
