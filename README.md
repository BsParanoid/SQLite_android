# SQLite_android
Full Tutorial with every steps explained

La classe principal qui permet la création de la base de données est **SQLiteOpenHelper**

Au moment de la création de la base de données la méthode _**void onCreate(SQLiteDatabase db)**_ est appellé le paramètre db est la base.

##1. Création de la Table avec ses différentes colonnes 

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
                    DB_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DB_NAME + " TEXT, " +
                    DB_SURNAME + " TEXT, " +
                    DB_AGE + " REAL);";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_TABLE_PEOPLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
```

**Voici la représentation de la table SQLite**


| name          | surname       | age   |
| ------------- |:-------------:| -----:|
| XXXX          | XXXX          |  XX   |
| XXXX          | XXXX          |  XX   |
| XXXX          | XXXX          |  XX   |
