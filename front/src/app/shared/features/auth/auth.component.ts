import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';  


@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent {
 email = '';
  password = '';
  message = '';

  constructor(private http: HttpClient) {}

 onSubmit() {
  this.http.post('http://localhost:8080/account', {
      id: 0,                    // met 0 ou null pour forcer la création
      username: 'user',         // valeur par défaut ou ajout dans ton formulaire
      firstname: 'User',        // idem
      email: this.email,
      password: this.password
    }).subscribe({
      next: () => this.message = 'Compte créé avec succès',
      error: () => this.message = 'Erreur lors de la création'
    });
  }
}

  /*
{
  "email": "admin@admin.com",
  "password": "monMotDePasseSecret"
}

  */