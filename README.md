# SQLite_android
Full Tutorial with every steps explained

La classe principal qui permet la création de la base de données est **SQLiteOpenHelper**

Au moment de la création de la base de données la méthode _**void onCreate(SQLiteDatabase db)**_ est appellé le paramètre db est la base.

1. Création de la Table avec ses différentes colonnes 

{
public class DatabaseHandler extends SQLiteOpenHelper
{
    public static final String DB_KEY = "id";              // Clef **ID** qui est un entier auto-incrémental
    public static final String DB_NAME = "name";           // Nom d'une colonnes de la TABLE **BD_TABLE_PEOPLE**
    public static final String DB_SURNAME = "surname";     // Nom d'une colonnes de la TABLE **BD_TABLE_PEOPLE**
    public static final String DB_AGE = "age";             // Nom d'une colonnes de la TABLE **BD_TABLE_PEOPLE**

    public static final String DB_TABLE_PEOPLE = "people";

    public static final String DB_TABLE_PEOPLE_CREATE =
            "CREATE TABLE " + DB_TABLE_PEOPLE + " (" +
                    DB_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DB_NAME + " TEXT, " +
                    DB_SURNAME " TEXT, " +
                    DB_AGE + " REAL);";
}
