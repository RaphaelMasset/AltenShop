# Initialiser le projet Spring Boot
    - Crée un projet avec Spring Initializr
        - Choix Maven
        - Java 17+ 
        - Créer la classe modèle Product
```
curl -k https://start.spring.io/starter.zip \
  -d dependencies=web \
  -d javaVersion=17 \
  -d language=java \
  -d type=maven-project \
  -d groupId=com.example \
  -d artifactId=altenshop-backend \
  -o altenshop-backend.zip

mvn spring-boot:run

```

# Gérer la persistence dans un fichier JSON
    - Crée un service ProductService qui :
        - Lit le fichier JSON dans une liste List<Product> en mémoire au démarrage.
        - Fournit les méthodes CRUD : getAll(), getById(), create(), update(), delete().
        - Après chaque modification (create/update/delete), écrit la liste modifiée dans le fichier JSON pour persister.
    - Utilise la bibliothèque Jackson (ObjectMapper) pour serialisation JSON.

```

public List<Product> loadProducts() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File("products.json");
    if (!file.exists()) {
        return new ArrayList<>();
    }
    return Arrays.asList(objectMapper.readValue(file, Product[].class));
}


```
Écriture dans JSON après modif :

```

    public void saveProducts(List<Product> products) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("products.json"), products);
    }


```

# Créer un contrôleur REST ProductController

```
@GetMapping("/products")
public List<Product> getAllProducts() {
    return productService.getAll();
}


```