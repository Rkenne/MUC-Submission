package com.example.robbie.gymapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Robbie on 14/12/2015.
 */
public class passTheTime extends AppCompatActivity {
    /*
    creates a canvas object and allows user to interact with the canvas. onCreate sets the layout to the canvas xml, which has
    contained a signature canvas which links to the passTheCanvas class. Uses a button to clear the canvas if pressed.
     */
    MediaPlayer myPlaya;
    private passTheCanvas myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas);

        myCanvas = (passTheCanvas)findViewById(R.id.signature_canvas);
        myPlaya = MediaPlayer.create(this, R.raw.click);
    }
    public void clearCanvas(View v)
    {
        myPlaya.start();
        myCanvas.clearCanvas();
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
                return true;

            default:
                return super.onOptionsItemSelected(i);
        }

    }
}
