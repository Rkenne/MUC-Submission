package com.example.robbie.gymapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
// instantiates the buttons on the landing page, My Exercise log, Gym Location, Pass The Time and About. Also
// creates a new MediaPlayer object to allow the click sound to be played
    Button myExercise;
    Button myGym;
    Button time;
    Button about;
    MediaPlayer myPlaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onCreate, sets the content view and buttons to the xml defined in activity_main
        // links myPlaya to raw sound and creates the object. this is then referenced via start method for each click.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myExercise = (Button) findViewById(R.id.btnExercise);
        myGym = (Button) findViewById(R.id.btnGym);
        time = (Button) findViewById(R.id.btnTime);
        about = (Button)findViewById(R.id.btnAbout);
        myPlaya = MediaPlayer.create(this, R.raw.click);


    }


    @Override
    public void onClick(View v) {
        //override method which checks which button is pressed and starts the appropriate intent. Each
        // time a button is pressed the previous activity closes and a new one starts
        switch (v.getId()) {
            case R.id.btnExercise:
                myPlaya.start();
                Intent i = new Intent(getApplicationContext(), displayMyExerciseLog.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.btnGym:
                myPlaya.start();
                Intent j = new Intent(getApplicationContext(), gymLocator.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                break;
            case R.id.btnTime:
                myPlaya.start();
                Intent k = new Intent(getApplicationContext(), passTheTime.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(k);
                break;
            case R.id.btnFeed:
                myPlaya.start();
                Intent l = new Intent(getApplicationContext(), feedLoader.class);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                break;
            // builds about dialog. Uses drawable of dumbell and explains app's purpose
            case R.id.btnAbout:
                myPlaya.start();
                AlertDialog.Builder aboutDlg = new AlertDialog.Builder(this);
                aboutDlg.setIcon(R.drawable.ic_action_gym);
                aboutDlg.setTitle("About GymApp");
                aboutDlg.setMessage("This gym application is tailored for Robbie's Gym For Heroes, located in Dennistoun, Glasgow. \n" +
                        "You can navigate across the application using the options menu located on the action bar above. \n" +
                        "This app allows you to view your saved exercises, shows the gym's location, pulls in a feed of helpful bodybuilding exercises and you can pass the time by drawing on a custom canvas. \n" +
                        "We hope you enjoy our app and look out for more updates.");
                aboutDlg.setPositiveButton("Close Dialog", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                    aboutDlg.create();
                    aboutDlg.show();
                break;
        }
    }


    @Override
    //  inflates the menu and sets the menu to the XML in menu folder
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gymmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem i)
    {
        //Similar to buttons above, takes user to appropriate activity once option selected in menu
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
                // produces an alertdialog asking user if they wish to quit. If they select yes, the app closes completely.
                //No closes the dialog.
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


  //  private class HTTPDownloader extends AsyncTask<String, Integer, >

