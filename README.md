# SQLite_android
Utilisation basique

[SQLiteOpenHelper doc](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html#)

[Tutorial from vogella](http://www.vogella.com/tutorials/AndroidSQLite/article.html)

[vogella tutoriel traduit français](http://vogella.developpez.com/tutoriels/android/utilisation-base-donnees-sqlite/)



![alt tag](https://github.com/BsParanoid/SQLite_android/blob/master/screenshots/mainScreen.pdf)

![alt tag](https://github.com/BsParanoid/SQLite_android/blob/master/screenshots/test.png)

![alt tag](https://github.com/BsParanoid/SQLite_android/blob/master/screenshots/ok.png)


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

##MySQLiteHelper

```java
/**
 *  Initialisation of database with :
 *  database name, database id, database column
 */
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

##Comment

```java
/**
 *  Data you want to store into database.
 */

public class Comment {
    private long id;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return comment;
    }
}
```

##CommentsDataSource
```java
/**
 *  Réalise la connection avec la base de donnée -> MySQLiteHelper
 *  et l'ajout de données.
 */

public class CommentsDataSource
{

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.TABLE_COLUMN_ID,
            MySQLiteHelper.TABLE_COLUMN_COMMENT };

    /**
     *  Constructeur CommentsDataSource qui réalise une instance de la classe MySQLiteHelper
     *  Afin de créer la base de données.
     * @param context
     */
    public CommentsDataSource(Context context)
    {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public Comment createComment(String comment)
    {
        /**
         *  ContentValues is a name value pair, used to insert or update values into database tables.
         *  ContentValues object will be passed to SQLiteDataBase objects insert() and update() functions.
         *  Cursor is a temporary buffer area which stores results from a SQLiteDataBase query.
         */
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TABLE_COLUMN_COMMENT, comment);

        long insertId = database.insert(MySQLiteHelper.TABLE_NAME, null,
                values);
        /**
         *  insert, Convenience method for inserting a row into the database.
         *  table, 	String: the table to insert the row into
         *  nullColumnHack,  optional; may be null
         *  values, 	ContentValues: this map contains the initial column values for the row.
         *  The keys should be the column names and the values the column values
         */

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, MySQLiteHelper.TABLE_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        /**
         *  cursor's basically start at -1, thats why we called ->
         *  moveToFirst is called to set the cursor position at the first the entry wich is the first query.
         *  It will return false if the cursor is empty
         */
        cursor.moveToFirst();

        /**
         *  Set Class Comment values
         */
        Comment newComment = cursorToComment(cursor);

        /**
         *  Closes the cursor, releasing all of its resources and making it completely invalid
         */
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment)
    {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.TABLE_COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments()
    {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor)
    {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}
```

##TestDatabaseActivity

```java
public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);


        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();
        
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
    
    public void onClick(View view)
    {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch (view.getId()) {
            case R.id.add:
                EditText editText = (EditText) findViewById(R.id.editText);
                String comments = editText.getText().toString();
                comment = datasource.createComment(comments);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
```
