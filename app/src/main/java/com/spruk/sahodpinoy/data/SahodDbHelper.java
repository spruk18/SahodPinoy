package com.spruk.sahodpinoy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.spruk.sahodpinoy.data.SahodContract.SahodEntry;
/**
 * Created by taray on 10/6/2015.
 */
public class SahodDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "sahod.db";

    public SahodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SAHOD_TABLE = "CREATE TABLE " + SahodEntry.TABLE_NAME + " (" +
                SahodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SahodEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                SahodEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                SahodEntry.COLUMN_AMOUNT + " REAL NOT NULL " +
                " );";


        sqLiteDatabase.execSQL(SQL_CREATE_SAHOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SahodEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}