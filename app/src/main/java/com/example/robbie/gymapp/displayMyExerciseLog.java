package com.example.robbie.gymapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Robbie on 11/12/2015.
 */
public class displayMyExerciseLog extends AppCompatActivity {
private GridView grd;
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState){
        //onCreate, sets content view to exerciseLog xml, binds gridview to grid xml contain and creates new databasehandler
        //to load database. Then calls dbCreate and binds the database to the gridview using ArrayAdapter
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayexerciselog);
        Intent test = getIntent();
        grd = (GridView)findViewById(R.id.exerciseGrid);
        mContext = this;
        DatabaseHandler myDb = new DatabaseHandler(this, "exerciselog.s3db", null, 1);

        //read the database]
        try
        {
            myDb.dbCreate();


        }catch(IOException e){
            e.printStackTrace();
        }
        List<myExerciseLog> myExercises = myDb.getAllExercises();
        ArrayAdapter<myExerciseLog> adapter = new ArrayAdapter<myExerciseLog>
                (mContext, android.R.layout.simple_list_item_1, myExercises);
        grd.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gymmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem i)
    {
        switch(i.getItemId())
        {
            case R.id.gymLocation:
                Intent j = new Intent(getApplicationContext(), gymLocator.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                return true;
            case R.id.rssfeed:
                Intent l = new Intent(getApplicationContext(), feedLoader.class);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                return true;
            case R.id.myExercises:
                Intent w = new Intent(getApplicationContext(), displayMyExerciseLog.class);
                w.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(w);
                return true;
            case R.id.myCanvas:
                Intent z = new Intent(getApplicationContext(), passTheTime.class);
                z.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(z);
                return true;
            case R.id.myHome:
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                return true;
            case R.id.quit:
                AlertDialog.Builder exitCase = new AlertDialog.Builder(this);
                exitCase.setMessage("Are you sure you want to quit?");
                exitCase.setPositiveButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dg, int which) {
                        dg.cancel();
                    }
                })
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        });
                AlertDialog alert = exitCase.create();
                exitCase.show();


            default:
                return super.onOptionsItemSelected(i);
        }

    }
}
