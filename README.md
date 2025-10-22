# Consignes

- Vous êtes développeur front-end : vous devez réaliser les consignes décrites dans le chapitre [Front-end](#Front-end)

- Vous êtes développeur back-end : vous devez réaliser les consignes décrites dans le chapitre [Back-end](#Back-end) (*)

- Vous êtes développeur full-stack : vous devez réaliser les consignes décrites dans le chapitre [Front-end](#Front-end) et le chapitre [Back-end](#Back-end) (*)

(*) Afin de tester votre API, veuillez proposer une stratégie de test appropriée.

## 1. Front-end

Le site de e-commerce d'Alten a besoin de s'enrichir de nouvelles fonctionnalités.

### Partie 1.1 : Shop

- 1.1.1. Afficher toutes les informations pertinentes d'un produit sur la liste  
- 1.1.2. Permettre d'ajouter un produit au panier depuis la liste  
- 1.1.3. Permettre de supprimer un produit du panier  
- 1.1.4. Afficher un badge indiquant la quantité de produits dans le panier  
- 1.1.5. Permettre de visualiser la liste des produits qui composent le panier  

### Partie 1.2 : Contact

- 1.2.1. Créer un nouveau point de menu dans la barre latérale ("Contact")  
- 1.2.2. Créer une page "Contact" affichant un formulaire  
- 1.2.3. Le formulaire doit permettre de saisir son email, un message et de cliquer sur "Envoyer"  
- 1.2.4. Email et message doivent être obligatoirement remplis, message doit être inférieur à 300 caractères  
- 1.2.5. Quand le message a été envoyé, afficher un message à l'utilisateur : "Demande de contact envoyée avec succès" 

### Partie 1.3 : Bonus

- 1.3.1. Ajouter un système de pagination et/ou de filtrage sur la liste des produits  
- 1.3.2. Permettre de visualiser et ajuster la quantité des produits depuis la liste et depuis le panier  

## 2. Back-end

### Partie 2.1 : API Produits

Développer un back-end permettant la gestion de produits définis plus bas.  
Vous pouvez utiliser la technologie de votre choix parmi :  
- Node.js/Express  
- Java/Spring Boot  
- C#/.NET Core  
- PHP/Symfony (Utilisation d’API Platform interdite)  

Un produit a les caractéristiques suivantes : 

``` typescript
class Product {
  id: number;
  code: string;
  name: string;
  description: string;
  image: string;
  category: string;
  price: number;
  quantity: number;
  internalReference: string;
  shellId: number;
  inventoryStatus: "INSTOCK" | "LOWSTOCK" | "OUTOFSTOCK";
  rating: number;
  createdAt: number;
  updatedAt: number;
}
```

Le back-end créé doit pouvoir gérer les produits dans une base de données SQL/NoSQL ou dans un fichier json.

### Partie 2.2 : Gestion des données

- Imposer à l'utilisateur de se connecter pour accéder à l'API.
  La connexion doit être gérée en utilisant un token JWT.  
  Deux routes devront être créées :
  * [POST] /account -> Permet de créer un nouveau compte pour un utilisateur avec les informations fournies par la requête.   
    Payload attendu : 
    ```
    {
      username: string,
      firstname: string,
      email: string,
      password: string
    }
    ```
  * [POST] /token -> Permet de se connecter à l'application.  
    Payload attendu :  
    ```
    {
      email: string,
      password: string
    }
    ```
    Une vérification devra être effectuée parmi tout les utilisateurs de l'application afin de connecter celui qui correspond aux infos fournies. Un token JWT sera renvoyé en retour de la reqûete.
- Faire en sorte que seul l'utilisateur ayant le mail "admin@admin.com" puisse ajouter, modifier ou supprimer des produits. Une solution simple et générique devra être utilisée. Il n'est pas nécessaire de mettre en place une gestion des accès basée sur les rôles.
- Ajouter la possibilité pour un utilisateur de gérer un panier d'achat pouvant contenir des produits.
- Ajouter la possibilité pour un utilisateur de gérer une liste d'envie pouvant contenir des produits.

### Partie 2.4 : Bonus

Vous pouvez ajouter des tests Postman ou Swagger pour valider votre API