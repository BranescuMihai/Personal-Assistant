package com.razbyte.personalassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MoveDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movetable.db";
    private static final int DATABASE_VERSION = 1;

    public MoveDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        MoveTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        MoveTable.onUpgrade(database, oldVersion, newVersion);
    }

}
