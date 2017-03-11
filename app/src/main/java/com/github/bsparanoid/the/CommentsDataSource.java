package com.github.bsparanoid.the;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bsparanoid on 11/03/17.
 */

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