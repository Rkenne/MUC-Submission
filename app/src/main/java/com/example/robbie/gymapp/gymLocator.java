package com.example.robbie.gymapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Robbie on 22-Nov-15.
 */
public class gymLocator extends AppCompatActivity implements View.OnClickListener {


    static final LatLng gymPoint = new LatLng(55.8606430, -4.2191330);
    private GoogleMap myMap;
    Camera myCam;
    Button changeView;
    private int isPressed;
    MediaPlayer myPlaya;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgymlocator);
        Intent test = getIntent();
        changeView = (Button)findViewById(R.id.btnView);
        changeView.setOnClickListener(this);
        myPlaya = MediaPlayer.create(this, R.raw.click);


                try {
                    if (myMap == null) {
                        myMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    }
                    myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    myMap.getUiSettings().setZoomGesturesEnabled(true);
                    Marker mrk = myMap.addMarker(new MarkerOptions().position(gymPoint).title("Robbie's Gym For Heroes"));
                    moveToMapPointer(gymPoint);

///
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
    @Override
    //plays sound for click and changes view. if isPressed equals 2 then view is changed back to Normal and counter reset.
    public void onClick(View v)
    {
        myPlaya.start();
        myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        isPressed = isPressed + 1;

        if(isPressed == 2)
        {
            myPlaya.start();
            myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            isPressed = 0;
        }
    }


    private void moveToMapPointer(LatLng zoomLocale)
    {
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomLocale, 17));
        //zoom in on the location
        myMap.animateCamera(CameraUpdateFactory.zoomIn());

        myMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2100, null);
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



