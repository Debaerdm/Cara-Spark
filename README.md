# Bienvenue sur la formation pour SparkJava !

## À propos 
**Spark** est un framework utilisé en java (ne pas confondre avec le framework d'apache). Ce framework permet entre autres de pouvoir facilement gérer des **microservices**. Le framework a pour vocation de réduire au minimum le code nécessaire à la mise en place du serveur responsable des microservices.

Nous vous fournirons le squelette du projet qui comportera les classes du modèle ainsi que les différents éléments permettant d'afficher les infos lors de l'accès aux pages web (html, css, js ...). Votre but sera de mettre en place le serveur qui fera le lien entre le modèle et la vue grâce au framework Spark.

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
### À savoir
 - Toutes vos modifications seront à faire dans la classe `MoutainController.java`. Le modèle et la vue fonctionnent et n'ont pas besoin d'être modifiés.

### Partie 1
  Dans cette partie, votre but sera de faire fonctionner la page d'accueil de l'application. Pour cela vous aurez besoin de rajouter dans la methode `setupEndpoints()` l'appel à Spark qui permettra de créer une route récupérant la liste des donjons de la montagne afin qu'ils puissent être affichés.
  
  La méthode HTTP utilisée est la méthode **get**. Le type de retour doit être une **Map<String, Dungeon>**. L'URL à laquelle la vue doit récupérer les données est **"/api/mountain"**. Les données transmises doivent être également transformées en Json (la documentation devrait vous aider pour ce dernier point).
  
### Partie 2
 La barre d'entête comprend de nombreux objets qui n'ont pas pu être récupérés par le biais du microservice. Nous allons d'abord nous intéresser à la valeur {{total}}. Cette valeur doit être initialisée avec le nombre de donjons présents dans la montagne. Comme pour la partie 1, faites appel à Spark pour mettre en place un retour sur l'URL **"/api/dungeon/dungeon_total"**. Cette méthode devra renvoyer un **int** comme type de retour, toujours **transformé en Json** au préalable.
 Une autre valeur intéressante serait de savoir si la personne demandant l'accès à la page possède un donjon. Nous vous laissons chercher un petit peu, notez que les méthodes get, post, put, etc... de Spark prennent en méthode la requête reçue et la réponse renvoyée, ce qui devrait vous aider à renvoyer la bonne valeur lors de l'appel à l'URL **"/api/dungeon/exist"**. N'oubliez pas la **transformation en Json** !
  
### Partie 3
  Maintenant que vous avez les informations de base, nous allons nous attaquer à une requête via la méthode HTTP **post**. En appelant l'URL **"/api/join"**, nous voulons permettre à l'utilisateur de rejoindre la montagne, c'est-à-dire de lui créer un nouveau donjon, en le référençant grâce à son IP sous forme de String. S'il a déjà un donjon, il ne faudra pas en créer un nouveau. En revanche, dans tous les cas, nous souhaitons ensuite le rediriger sur l'URL **"/api/dungeon/maps"** via une requête **get**, peu importe qu'un nouveau donjon aie été créé ou que la personne aie déjà un donjon.

### Partie 4
  Vous l'aurez remarqué, toutes nos URL faisant appel au microservice commencent par **"/api"**. C'est un peu redondant, mais Spark permet d'aider par la création de groupe de routes. Pour cela, utilisez la méthode Spark.path() en lui donnant le préfixe commun à toutes les URL, et en donnant en deuxième paramètre une lambda de la forme () -> { } où vous pourrez déclarer vos routes dans les crochets. La documentation devrait pouvoir vous aider avec un exemple (Point **"path-group"**).
  
  Deux nouvelles méthodes sont nécessaires pour continuer. Nous voulons connaître la disposition du donjon, dans un premier temps. Il nous faut donc le récupérer. Pour cela, un appel à l'URL **"/api/maps"** via la méthode HTTP **"get"** devrait nous renvoyer une grille d'objets Tile à deux dimension (type de retour "Tile[][]") correspondant à la carte du donjon correspondant à l'IP de l'expéditeur de la requête.
  Dans un second temps, nous aimerions 
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
  
