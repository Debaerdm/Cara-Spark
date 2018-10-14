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
  Par la même occasion, nous allons simplifier un peu les routes définies jusqu'à maintenant. Chacune d'entre elles fait appel à une transformation en Json lors de l'envoi de la réponse, c'est à dire **après** traitement. Autrement dit, **après** tous les appels aux URL, le **type** de l'objet **réponse** est spécifié comme étant du Json. La documentation devrait pouvoir vous expliquer comment faire (Point **"filters"**).

### Partie 4
  Vous l'aurez remarqué, toutes nos URL faisant appel au microservice commencent par **"/api"**. C'est un peu redondant, mais Spark permet d'aider par la création de groupe de routes. Pour cela, utilisez la méthode Spark.path() en lui donnant le préfixe commun à toutes les URL, et en donnant en deuxième paramètre une lambda de la forme () -> { } où vous pourrez déclarer vos routes dans les crochets. La documentation devrait pouvoir vous aider avec un exemple (Point **"path-group"**).
  
  Deux nouvelles méthodes sont nécessaires pour continuer. Nous voulons connaître la disposition du donjon, dans un premier temps. Il nous faut donc le récupérer. Pour cela, un appel à l'URL **"/api/maps"** via la méthode HTTP **"get"** devrait nous renvoyer une grille d'objets Tile à deux dimension (type de retour "Tile[][]") correspondant à la carte du donjon correspondant à l'IP de l'expéditeur de la requête.
  Dans un second temps, nous aimerions récupérer tous les types de ressources du modèle, afin de pouvoir proposer aux joueurs de construire des bâtiments afin de les produire, et cela même si nous souhaitons en ajouter d'autres plus tard. Pour cela, les requêtes à l'URL **"/api/itemTypes"** devraient renvoyer une **Map<ItemType, Integer>** contenant les différents types de ressources avec le coût du bâtiment de production associé. Si vous avez fait la partie 3, vous ne devriez normalement plus avoir à vous embêter à spécifier le type de retour comme étant du Json !
  
  ### Partie 5
  Les joueurs ont un donjon, mais ont également des stocks de ressources que nous souhaiterions afficher en haut, dans le header, à la place de {{rock}}, {{gold}} et {{gems}}. Pour cela, trois méthodes get aux URL respectives **"/api/dungeon/rock"**, **"/api/dungeon/gold"** et **"/api/dungeon/gems"** devraient renvoyer les valeurs de l'inventaire du donjon correspondant à l'IP de la requête. Vous devriez savoir vous débrouiller sans beaucoup plus d'aide maintenant.
      
  ### Partie 6
  Pour la dernière partie guidée de ce petit projet, nous allons permettre au joueur de créer les bâtiments de production. Pour cela, nous allons préciser à Spark de recevoir une méthode **put** à l'URL **"/api/dungeon/build"**. En paramètre, la vue va renvoyer des informations en Json sous la forme d'une **Map<String, Object>** que vous devrez parser. Dans cette Map, la clef "buildItem" vous donnera un objet de type String qui vous renverra un ItemType si vous le passer comme paramètre à ItemType.valueOf(), vous permettant de connaître le type de bâtiment à créer. Deux objets de type **Double** seront également renvoyés dans cette Map aux clefs "row" et "col" respectivement pour la ligne et la colonne de la case où construire le bâtiment. Il faudra les transformer en **int** pour les faire correspondre aux coordonnées du modèle. Avec toutes ces informations, vous devriez être en mesure d'appeler la méthode build() de l'objet Dungeon pour construire le bâtiment, et de renvoyer la constante correspondant au code de retour HTTP_OK.
  
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
  
