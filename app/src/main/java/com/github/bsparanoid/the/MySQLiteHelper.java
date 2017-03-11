package com.github.bsparanoid.the;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bsparanoid on 11/03/17.
 */

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