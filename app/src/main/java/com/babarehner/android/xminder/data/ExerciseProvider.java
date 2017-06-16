package com.babarehner.android.xminder.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.babarehner.android.xminder.data.ExerciseContract.CONTENT_AUTHORITY;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.CARDIO_ITEM_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.CARDIO_LIST_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.STRENGTH_ITEM_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.STRENGTH_LIST_TYPE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.TCARDIO;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.TSTRENGTH;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry._IDS;
import static com.babarehner.android.xminder.data.ExerciseContract.PATH_CARDIO;
import static com.babarehner.android.xminder.data.ExerciseContract.PATH_STRENGTH;

/**
 * Created by mike on 6/16/17.
 */

public class ExerciseProvider extends ContentProvider {

    // Tag for log messages
    public static final String LOG_TAG  = ExerciseProvider.class.getSimpleName();

    private ExerciseDBHelper mDBHelper;

    // URI matcher int for strength exercises from the TStrength table
    private static final int STRENGTH_LIST = 100;
    private static final int STRENGTH_ID = 105;
    private static final int CARDIO_LIST = 200;
    private static final int CARDIO_ID = 205;

    /**
     * URIMatcher object to match a content URI to a correpsodig int.
     * The input passed into the constructor represents the int to return for
     * the root URI
     * It's common to use NO_MATCH for this case
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // static initializer- run for the first time anything called from this class.
    static {
        /**
         * The calls to addURi() go here for all of the content URi patterns that the
         * proveder should recognize. All paths added to the urimatcher should have a
         * corresponding code to return when a match is found.
         */
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_STRENGTH, STRENGTH_LIST);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_STRENGTH + "/#", STRENGTH_ID);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_CARDIO, CARDIO_LIST);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_CARDIO + "/#", CARDIO_ID);
    }


    @Override
    public boolean onCreate() {
        mDBHelper = new ExerciseDBHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI, use the given projection, selection
     * arguments and sort order
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c;       //Hold the results of the query

        // determine if the URI  matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case STRENGTH_LIST:
               ; c = db.query(TSTRENGTH, projection, selection, selectionArgs,
                    null, null, sortOrder);
                break;
            case STRENGTH_ID:
                selection = _IDS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                c = db.query(TSTRENGTH, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CARDIO_LIST:
                ; c = db.query(TCARDIO, projection, selection, selectionArgs,
                    null, null, sortOrder);
                break;
            case CARDIO_ID:
                selection = _IDS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                c = db.query(TCARDIO, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(" Cannnot query with unknown URI " + uri);
        }

        /**
         * Set notification urI on the cursor so we know what content th4 UrI the curwor
         * was created for. If the data at this URI changes, then we need to update the
         * cursor
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    // TODO Implement update
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    // TODO implement insert
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    // TODO implement delete
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        int deleteCount = 0;
        return deleteCount;
    }


    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STRENGTH_LIST:
                return STRENGTH_LIST_TYPE;
            case STRENGTH_ID:
                return STRENGTH_ITEM_TYPE;
            case CARDIO_LIST:
                return CARDIO_LIST_TYPE;
            case CARDIO_ID:
                return CARDIO_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match = " + match);
        }
    }
}
