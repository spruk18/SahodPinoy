package com.spruk.sahodpinoy.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by taray on 10/6/2015.
 */
public class SahodContract {


    public static final String CONTENT_AUTHORITY = "com.example.sahodpinoy";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SAHOD = "sahod";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.setToNow();
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /* Inner class that defines the table contents of the location table */
    public static final class SahodEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SAHOD).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SAHOD;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SAHOD;

        // Table name
        public static final String TABLE_NAME = "sahod";

        public static final String COLUMN_DATE = "ddate";
        public static final String COLUMN_TYPE = "type";

        public static final String COLUMN_AMOUNT = "amount";

        public static Uri buildSahodUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}