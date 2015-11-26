package com.eip.projecthandler.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    public void onUpgrade(SQLiteDatabase db, int version_old, int version_new) {
        db.execSQL(drop_table);
        db.execSQL(create_table);
    }

    private static final String create_table = "create table server(" +
            "ID integer primary key autoincrement, " +
            "ADDRESS text" +
            ")";

    private static final String drop_table = "drop table server";
}
