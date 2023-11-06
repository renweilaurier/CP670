package com.example.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "Messages.db";
    public static int VERSION_NUM = 1;
    public static String TABLE_NAME = "CHAT_HISTORY";
    public static String KEY_ID = "id";
    public static String KEY_MESSAGE = "MESSAGE";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        String createTableSql = "create table " + TABLE_NAME + " ( " + KEY_ID
                + " integer primary key autoincrement, " + KEY_MESSAGE + " text not null);";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        String dropTableSql = "drop table if exists " + TABLE_NAME + ";";
        db.execSQL(dropTableSql);
        onCreate(db);
    }
}
