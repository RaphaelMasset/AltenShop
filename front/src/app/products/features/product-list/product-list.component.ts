import { Component, OnInit, inject, signal,Output } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { CommonModule } from '@angular/common';
import { CartService } from 'app/cart.service'; // adapte le chemin
import { FormsModule } from '@angular/forms';

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [CommonModule,FormsModule,DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent],
})
export class ProductListComponent implements OnInit {
  private cartService = inject(CartService);
  private readonly productsService = inject(ProductsService);
  //of course the user id need to be check on the back end side
  currentUserEmail = "admin@admin.com";
  currentPage = 0;
  rowsPerPage = 10;

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);
  filterText = '';
  filteredProducts: Product[] = [];

  ngOnInit() {
    this.productsService.get().subscribe(() => {
      console.log('Premier produit reçu :', this.products()[0]);
      this.applyFilter();
    });
   
  }
  applyFilter() {
    const filter = this.filterText.toLowerCase().trim();
    this.filteredProducts = this.products()
      .filter(product => product.name.toLowerCase().includes(filter));
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  // pour lire le panier dans le template, crée une getter
  get cart() {
    return this.cartService.cart();
  }

  addToCart(product: Product) {
    this.cartService.addToCart(product);
  }

  removeFromCart(product: Product) {
    this.cartService.removeFromCart(product);
  }

  getQuantityInCart(product: Product): number {
    const item = this.cart.find(p => p.id === product.id);
    return item ? item.quantity : 0;
  }

  isInCart(product: Product): boolean {
    return this.getQuantityInCart(product) > 0;
  }
  
  onPageChange(event: any) {
    this.currentPage = event.first / event.rows;
    this.rowsPerPage = event.rows;
    // Charger ou filtrer les données si nécessaires (surtout pour serveur)
  }

  onImageError(event: Event) {
    const element = event.target as HTMLImageElement;
    element.src = 'assets/images/default.jpg';  // Chemin de ton image par défaut
  }

}
