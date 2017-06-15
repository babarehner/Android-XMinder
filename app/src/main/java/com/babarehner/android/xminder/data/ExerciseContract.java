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

    /**
     * Possible pathe appended to base content URIs for possible URIs. For instance,
     * content.com.babarehner.android.xminder/couchpotato will fail because the
     * ContentProvider hasn't been given any information on what to do with
     * "couchpotato"
     */
    public static final String PATH_STRENGTH = "TStrength";
    public static final String PATH_CARDIO = "TCardio";


    /**
     * Inner class that defines constant values for the Exercise database tables
     * Each entry in the table represents a single exercise type
     */
    public static class ExerciseEntry implements BaseColumns {

        // The MIME type if the {@link #CONTENT_URI} for a list of Strength Exercises
        public static final String STRENGTH_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_STRENGTH;
        // The MIME type if the {@link #CONTENT_URI} for a list of Strength Exercises
        public static final String CARDIO_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_CARDIO;
        // The MIME type if the {@link #CONTENT_URI} for a single Strength Exercises
        public static final String STRENGTH_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_STRENGTH;
        // The MIME type if the {@link #CONTENT_URI} for a single Strength Exercises
        public static final String CARDIO_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_CARDIO;

        // Name of table for strength training
        public static final String TSTRENGTH = "TStrength";
        // primary key to be autoincrements.
        // I think BaseColumns are required for ContentProviders
        public static final String _IDS = BaseColumns._ID;
        public static final String NUM = "Num";
        public static final String EX_NAME = "ExName";
        public static final String WEIGHT = "Weight";
        public static final String REPS = "Reps";
        public static final String NOTE = "Note";

        // name of table for cardio training
        public static final String TCARDIO = "TCardio";




    }

}
