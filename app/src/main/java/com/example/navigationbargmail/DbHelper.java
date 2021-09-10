package com.example.navigationbargmail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="database_one";
    public static final int DATABASE_VIRSION=1;
    public static final String CREATE_TABLE="CREATE TABLE user_data(username String ,id String)";
    SQLiteDatabase db;

    public DbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VIRSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deletewishlist(String id) {
        // on below line we are creating
        // a variable to write our database.
       db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete("user_data", "id=?", new String[]{id});
        db.close();
    }



}
