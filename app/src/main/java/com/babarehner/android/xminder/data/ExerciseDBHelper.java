package com.babarehner.android.xminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.C_DATE;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.C_EX_NAME;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.C_GRAPHIC;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.C_NOTES;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.C_ORDER;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.C_REPS;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.C_WEIGHT;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry.TSTRENGTH;
import static com.babarehner.android.xminder.data.ExerciseContract.ExerciseEntry._IDS;

/**
 * Created by mike on 6/14/17.
 */


// TODO Take a long look at the DB DESIGN and see if I can rework it similatr to ACCESS DB
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
        String SQL_CREATE_STRENGTH_TABLE = "CREATE TABLE " + TSTRENGTH
                + "("
                + _IDS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C_ORDER  + " INTEGER, "
                + C_EX_NAME + " TEXT NOT NULL, "
                + C_WEIGHT + " INTEGER, "
                + C_REPS + " INTEGER, "
                + C_GRAPHIC + " TEXT, "
                + C_NOTES + " TEXT, "
                + C_DATE + " TEXT);";

        db.execSQL(SQL_CREATE_STRENGTH_TABLE);

        // TODO Design and build CARDIO table

    }

    // required for class SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
