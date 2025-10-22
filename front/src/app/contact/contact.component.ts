import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
//FormsModule et ReactiveFormsModule gèrent les formulaires et leur validation
//FormBuilder et Validator simplifient la création et la validation des contrôles de formulaire.
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
//composants UI pour saisir du texte, afficher des boutons et messages.
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, ButtonModule, InputTextModule, InputTextareaModule, MessagesModule, MessageModule],
  templateUrl: "./contact.component.html",
  styleUrls: ["./contact.component.css"],
  providers: [MessageService]
})
export class ContactComponent {
  //créé avec FormBuilder.group contenant deux champs : email et message.
  contactForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    message: ['', [Validators.required, Validators.maxLength(300)]]
  });
  successMessage = '';

  constructor(private fb: FormBuilder) {}

  get email() {
    return this.contactForm.get('email')!;
  }

  get message() {
    return this.contactForm.get('message')!;
  }

  onSubmit() {
    if (this.contactForm.valid) {
      // Simuler envoi de message
      this.successMessage = 'Demande de contact envoyée avec succès';
      this.contactForm.reset();
    }
  }
}
