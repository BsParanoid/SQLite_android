# SQLite_android
Utilisation basique

[SQLiteOpenHelper doc](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html#)

[Tutorial from vogella](http://www.vogella.com/tutorials/AndroidSQLite/article.html)

[vogella tutoriel traduit français](http://vogella.developpez.com/tutoriels/android/utilisation-base-donnees-sqlite/)



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
public class MySQLiteHelper extends SQLiteOpenHelper
{

    public static final String TABLE_NAME = "comments";
    public static final String TABLE_COLUMN_ID = "_id";
    public static final String TABLE_COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + TABLE_COLUMN_ID
            + " integer primary key autoincrement, " + TABLE_COLUMN_COMMENT
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
```
