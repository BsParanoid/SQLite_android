# SQLite_android
Utilisation basique

[SQLiteOpenHelper doc](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html#)


La classe principal qui permet la création de la base de données est **SQLiteOpenHelper**

Au moment de la création de la base de données la méthode _**void onCreate(SQLiteDatabase db)**_ est appellé le paramètre db est la base.

##1. Création de la Table avec ses différentes colonnes 

#### 1.2 Composition de "CREATE TABLE"

La commande "CREATE TABLE" est utilisée pour créer une nouvelle table dans une base de données SQLite. Une commande CREATE TABLE spécifie les attributs suivants de la nouvelle table :

* Nom de la nouvelle table.
* Le type déclaré de chaque colonne dans le tableau.
* Valeur ou expression par défaut pour chaque colonne de la table.
* Une séquence de classement par défaut à utiliser avec chaque colonne.
* Optionnellement, une PRIMARY KEY pour la table. Les clés primaires à une seule colonne et composites (plusieurs colonnes) sont prises en charge.
* Un ensemble de contraintes SQL pour chaque table. SQLite prend en charge les contraintes UNIQUE, NOT NULL, CHECK et FOREIGN KEY.
*  Si la table est une table WITHOUT ROWID.


```java
public class DatabaseHandler extends SQLiteOpenHelper
{
    public static final String DB_KEY = "id";
    public static final String DB_NAME = "name";
    public static final String DB_SURNAME = "surname";
    public static final String DB_AGE = "age";

    public static final String DB_TABLE_PEOPLE = "people";

    public static final String DB_TABLE_PEOPLE_CREATE =
            "CREATE TABLE " + DB_TABLE_PEOPLE + " (" +
                    DB_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID KEY auto increm
                    DB_NAME + " TEXT, " +                             // colonne de la table
                    DB_SURNAME + " TEXT, " +                          // colonne de la table
                    DB_AGE + " REAL);";                               // colonne de la table

     /*  **public constructeur DatabaseHandler**
    **  Créer un objet helper pour créer, ouvrir, et/ou manager une database.
    **  La database n'est actuellement pas créer ou ouverte tant que getWritableDatabase() ou getReadableDatabase() 
    **  n'est appellé.
    */
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) // fonction de création et d'initialisation de table
    {
        db.execSQL(DB_TABLE_PEOPLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {

    }
}
```

**Voici la représentation de la table SQLite**


| name          | surname       | age   |
| ------------- |:-------------:| -----:|
| XXXX          | XXXX          |  XX   |
| XXXX          | XXXX          |  XX   |
| XXXX          | XXXX          |  XX   |
