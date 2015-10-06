package com.spruk.sahodpinoy.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by taray on 10/6/2015.
 */
public class SahodProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SahodDbHelper dbHelper;

    static final int SAHOD = 100;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SahodContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,SahodContract.PATH_SAHOD,SAHOD);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new SahodDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri))
        {
            case SAHOD:
                retCursor = dbHelper.getReadableDatabase().query(
                        SahodContract.SahodEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;



            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match)
        {
            case SAHOD:
                return SahodContract.SahodEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch(match)
        {
            case SAHOD: {
                long _id = db.insert(SahodContract.SahodEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = SahodContract.SahodEntry.buildSahodUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if ( null == selection ) selection = "1";

        switch (match) {
            case SAHOD:
                rowsDeleted = db.delete(
                        SahodContract.SahodEntry.TABLE_NAME, selection, selectionArgs);
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case SAHOD:
                rowsUpdated = db.update(SahodContract.SahodEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
