package com.babarehner.android.xminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    // SQL statements to create the exercise DB (abstract method)
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    // required for class SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
