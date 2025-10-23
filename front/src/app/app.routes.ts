import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import { CartComponent } from './cart/cart.component';  // Adjust the path to your CartComponent
import { ContactComponent } from "./contact/contact.component";
import { AuthComponent } from "./shared/features/auth/auth.component";

export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  {
    path: 'cart',
    component: CartComponent, 
  },
  {
    path: 'contact',
    component: ContactComponent, 
  },
  {
    path: 'auth',
    component: AuthComponent
  },
  
  { path: "", redirectTo: "home", pathMatch: "full" },
];
