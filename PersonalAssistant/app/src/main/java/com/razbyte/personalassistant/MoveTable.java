package com.razbyte.personalassistant;

import android.database.sqlite.SQLiteDatabase;


public class MoveTable {

    public static final String TABLE_MOVE = "move";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_DISTANCE = "distance";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MOVE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PLACE + " text not null, "
            + COLUMN_DISTANCE + " real not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVE);
        onCreate(database);
    }
}
