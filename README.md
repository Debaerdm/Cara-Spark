# Bienvenue sur la formation pour SparkJava !

## À propos 
**Spark** est un framework utilisé en java (ne pas confondre avec le framework d'apache). Ce framework permet en autre de pouvoir facilement gérer des **microservices**. C'est-à-dire que avec une simple requête CRUD, un serveur géré avec Jetty se lance **automatiquement** et ouvre les routes adéquate !

**Documentation : [Site SparkJava](http://sparkjava.com/documentation)**

## Mise en place 

- Faire un git clone du repo :

```bash
git clone https://github.com/Debaerdm/Cara-Spark.git
cd Cara-Spark
```

- Lancer maven :

```bash
mvn clean install
mvn exec:java
```

#### Enjoy !

## Réalisation :
### A savoir
 - Tous vos modifications seront a faire dans la class `MoutainController.java`. A votre guise si vous voulez modifier votre serveur vers la fin du tp si vous avez fini avant.
 - Toutes les routes commence par "/api/**"

### Partie 1
  Dans cette partie votre but sera de faire fonctionner un minimun l'application. Pour cela vous aurez besoin de rajouter dans la methode `setupEndpoints()` une méthode get qui vous permettra de recupérer tous les donjons disponible dans la montagne. Aidez-vous de la class `Moutain.java` qui est un singleton. (Path : `/api/moutain`)
  
  Faire la partie 2 directement après.
  
### Partie 2
  Comment vous avez pu le constater le mieux serait de préciser le type de parametre qu'on envoie et le type de body qu'on reçoit. Pour cela il vous faudra faire un appel a la methode after de spark vous qu'il accepte que les données de type json. 
  
### Partie 3
  Dans cette partie vous allez attaquer le fait de rejoindre un donjon dans la montagne ! Cette fonction sera un post sur laquel vous allez utiliser le `request.ip()` libre a vous de le faire fonctionner avec le site ! (Path : `/api/join`)

### Partie 4
  Ici vous allez tous simplement utliser le `void path(String path, RouteGroup routeGroup)` de spark pour creer un groupe (lire la documentation) sur la route **/dungeon** dans ce groupe vous allez d'abord faire un before `void before(Filter... filters)` qui testera si vous êtes autoriser dans la montagne ! (voir la fonction `HaltException halt(int status, String body)`).
  
  Methode get :
  - Puis vous allez faire deux méthode qui permettra de recuperer si votre donjon existe toujours. (Path : `/api/dungeon/exist`)
  - Ensuite renvoyez le nombre total de dungeon. (Path : `/api/dungeon/dungeon_total`)
  
  ### Partie 5
  Cette partie permettra de gerer votre donjon ! 
    Deux méthodes get :
      - Recuperer la map de votre donjon ! (Path: `/api/dungeon/maps`)
      - Recuperer tous les items (type de blocs) pour les mines. (Path : `/api/dungeon/itemTypes`)
      
  ### Partie 6
  Ici vous allez recuperer vos ressources que vous disposez dans votre donjon (rock, gold, gems), c'est tres simple, il vous suffit de faire trois methodes get :
  
    - Pour recuperer la pierre.  (Path : `/api/dungeon/rock`)
    - Pour recuperer l'or. (Path : `/api/dungeon/gold`)
    - Pour recuperer les gemmes. (Path : `/api/dungeon/gems`)
    
  Il vous suffira de regarder les stocks disponible avec cette fonction disponible dans votre donjon : `getItemStock(ItemType type)` et de renvoyer sa valeur, 0 sinon.
 
  ### Partie 7
  Une méthode **put** sera utiliser et sa sera pour build vos mine ou de creuser la terre (enlever des murs pour rendre le terrain constructible). Votre but sera de recupere le body et l'ip dans "request", de parse le body avec la méthode disponible dans JsonTransformer de recuperer le coût du parametre "buildItem" (ItemType) du body. Voir si on a assez de resource sinon vous renvoyer une 404. Recuperer du body le row et col a transformer en int (Voir java.lang.Double) creer son BuildingType, build et renvoyer une 200. (Path : `/api/dungeon/build`)
  
### Tips
  - Aidez-vous de la méthode path qui vous permet de former des groupes de route !
  - Utilisez les "lambda expression". Ex :
```java
 get("/hello", (request, response) -> "Hello, World!", JsonTransformer.getInstance());
```
  Ici pas besoin de return car le fonction comprend qu'il y a qu'une ligne de code et que c'est celui la qu'il doit retourner. Par contre on peut faire cela qui est utiliser par plusieurs des méthodes de cette exercice faite atention ! Ex :
  ```java
 post("/newUser", (request, response) -> {
      request.session(true).attribute("user",request.body());
      return 200; // OK
 }, JsonTransformer.getInstance());
```
  - Pensez à utiliser les méthodes de JsonTransformer pour vos méthode http et les json 
  
