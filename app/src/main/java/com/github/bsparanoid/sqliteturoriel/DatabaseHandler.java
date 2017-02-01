package com.github.bsparanoid.sqliteturoriel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bsparanoid on 31/01/17.
 */


public class DatabaseHandler extends SQLiteOpenHelper
{
    public static final String DB_KEY = "id";
    public static final String DB_NAME = "name";
    public static final String DB_SURNAME = "surname";
    public static final String DB_AGE = "age";

    public static final String DB_TABLE_PEOPLE = "people";

    public static final String DB_TABLE_PEOPLE_CREATE =
            "CREATE TABLE " + DatabaseHandler.DB_TABLE_PEOPLE + " (" +
                    DatabaseHandler.DB_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseHandler.DB_NAME + " TEXT, " +
                    DatabaseHandler.DB_SURNAME + " TEXT, " +
                    DatabaseHandler.DB_AGE + " REAL);";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_TABLE_PEOPLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DatabaseHandler.DB_TABLE_PEOPLE); // Suppression de l'ancienne base de données

        onCreate(db); // upgrade des nouvelles données
    }
}

