package com.babarehner.android.xminder.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mike on 6/14/17.
 */

public final class ExerciseContract {

    // To prevent someone from accidentally instantiating the contract class
    // give it an empty constructor
    private ExerciseContract() { }

    /**
     * The 'Content Authority is a name for the entire content provider, similar to the
     * relationship between a domain name and its website. A covenient string to use for
     * the content authority is the package name for the app which is guaranteed to be
     * unique on the device
     */
    public static final String CONTENT_AUTHORITY = "com.babarehner.android.xminder";

    /**
     * Use CONTENT_AUTHORIY to create the base of all URI's which apps will use to contact
     * the content provider.<.parse() changes String to URI
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Usually is the table name you want to access- make sure you have the provider listed in the Manifest file
    public static final String PATH_TSTRENGTH = "TStrength";
    public static final String PATH_TCARDIO = "Cardio";


    /**
     * Inner class that defines constant values for the Exercise database tables
     * Each entry in the table represents a single exercise type
     */
    public static class ExerciseEntry implements BaseColumns {

        // The MIME type if the {@link #CONTENT_URI} for a list of Strength Exercises
        public static final String STRENGTH_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_TSTRENGTH;
        // The MIME type if the {@link #CONTENT_URI} for a list of Strength Exercises
        public static final String CARDIO_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_TCARDIO;
        // The MIME type if the {@link #CONTENT_URI} for a single Strength Exercises
        public static final String STRENGTH_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_TSTRENGTH;
        // The MIME type if the {@link #CONTENT_URI} for a single Strength Exercises
        public static final String CARDIO_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_TCARDIO;

        // The content uri to access the exercise data in the provider
        public static final Uri STRENGTH_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TSTRENGTH);
        public static final Uri CARDIO_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TCARDIO);

        // Name of table for strength training
        public static final String TSTRENGTH = "TStrength";
        // primary key to be autoincrements.
        // I think BaseColumns are required for ContentProviders
        public static final String _IDS = BaseColumns._ID;
        public static final String C_ORDER = "ListOrder";
        public static final String C_EX_NAME = "ExName";
        public static final String C_WEIGHT = "Weight";
        public static final String C_REPS = "Reps";
        public static final String C_GRAPHIC = "Graphic";
        public static final String C_NOTES = "Notes";
        public static final String C_DATE = "Date";
        public static final String C_SETS = "Sets";

        // name of table for cardio training
        public static final String TCARDIO = "TCardio";




    }

}
