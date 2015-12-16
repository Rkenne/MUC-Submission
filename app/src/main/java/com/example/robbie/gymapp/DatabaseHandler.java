package com.example.robbie.gymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Robbie on 11/12/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // static variables for database helper

    private static final int DATABASE_VERSION = 1;

    //DB NAME and Path
    public String DATABASE_PATH = "/data/data/com.example.robbie.gymapp/databases/";
    private static final String DATABASE_NAME = "exerciselog.s3db";


    //Table name in db

    private static final String TABLE_EXERCISE = "myExercise";

    // column names in table
    private static final String KEY_ID = "_ID";
    private static final String KEY_EXERCISE = "Exercise";
    private static final String KEY_REPS = "Reps";
    private static final String KEY_WEIGHT = "Weight";
    private final Context appContext;

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        // constructs the database, passes context, name of db, factory and version of db
        super(context, name, factory, version);
        this.appContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase exDb) {
        //onCreate, uses a string SQL command to create the table and then executes it with db object passed as parameter.

        String CREATE_EXERCISE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISE + "(" + KEY_ID+   " INTEGER PRIMARY KEY,"
                + KEY_EXERCISE+ " TEXT," +KEY_REPS+ " TEXT," + KEY_WEIGHT+  " TEXT" + ")";
                ;
        exDb.execSQL(CREATE_EXERCISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase exDb, int oldVersion, int newVersion) {
        //drop table if already exists
        if(newVersion>oldVersion) {
            exDb.execSQL("DROP TABLE IF EXISTS" + TABLE_EXERCISE);
            onCreate(exDb);
        }
    }

    // CRUD methods (not currently used, available for implementation

    public void addExercise(myExerciseLog newExercise) {
        SQLiteDatabase exDb = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXERCISE, newExercise.getExercise());
        values.put(KEY_REPS, newExercise.getReps());
        values.put(KEY_WEIGHT, newExercise.getWeight());

        //inserting row
        exDb.insert(TABLE_EXERCISE, null, values);
        exDb.close(); //exit connection

    }



    public List<myExerciseLog> getAllExercises()
    {
        //grabs all records in database and returns to user as a List. uses Cursor to iterate over records.
        List<myExerciseLog> allExercises = new ArrayList<>();
        // select all from the table
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE ;

        SQLiteDatabase exDb = this.getReadableDatabase();
        Cursor cursor = exDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

                while(cursor.isAfterLast() == false) {
                    myExerciseLog allEx = new myExerciseLog();
                    allEx.setExercise(cursor.getString(1));
                    allEx.setReps(cursor.getString(2));
                    allEx.setWeight(cursor.getString(3));
                    allExercises.add(allEx);
                    cursor.moveToNext();
                }
            cursor.close();


        }

        exDb.close();

        return allExercises;
    }
    public void dbCreate() throws IOException {
        // creates database and checks if database exists
        boolean dbExist = dbCheck();


        if(!dbExist)
        {
            //calling this method will create an empty database is dbExist is not true. creates the db
            //in the default system path of the application so we can overwrite that database with this one.

               this.getReadableDatabase();

            try{
                copyDBFromAssets();


            } catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }
    }
    private boolean dbCheck()
    {
        //Check if database exists. Uses Database path and name to fetch database from assets folder
        SQLiteDatabase db = null;

        try
        {
            String dbPath = DATABASE_PATH + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);

        }catch(SQLiteException e){

            Log.e("SQLHelper", "Database not Found!");
        }

        if(db != null){


            db.close();
        }

        return db != null;
    }

    private void copyDBFromAssets() throws IOException {
        //copys database from assets folder and outputs.
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DATABASE_PATH + DATABASE_NAME;

        try {

            dbInput = appContext.getAssets().open(DATABASE_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            //transfer bytes from the dbInput to the dbOutput
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }

            //close the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();


        } catch (IOException e) {
            throw new Error("Problems copying DB");

        }
    }
}
