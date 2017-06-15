package com.babarehner.android.xminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.EX_NAME;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.NOTE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.NUM;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.REPS;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.TSTRENGTH;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.WEIGHT;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry._IDS;

/**
 * Created by mike on 6/14/17.
 */

public class ExerciseDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ExerciseDBHelper.class.getSimpleName();
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Exercise.db";

    // class constructor used to create the db
    public ExerciseDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // SQL statements to create the tables and columsn in the DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_STRENGTH_TABE = "CREATE TABLE " + TSTRENGTH
                + "("
                + _IDS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NUM  + " INTEGER AUTOINCREMENT, "
                + EX_NAME + " TEXT NOT NULL, "
                + WEIGHT + " INTEGER "
                + REPS + " INTEGER"
                + NOTE + " TEXT);";

    }

    // required for class SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
