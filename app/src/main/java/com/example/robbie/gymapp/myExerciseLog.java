package com.example.robbie.gymapp;

import android.app.Activity;

/**
 * Created by Robbie on 22-Nov-15.
 */
public class myExerciseLog extends Activity {

    private int _ID;
    private String _Exercise;
    private String _Reps;
    private String _Weight;

    public int getID(){
        return _ID;
    }

    public void setID(int mUID)
    {
        _ID = mUID;
    }

    public String getExercise()
    {
        return _Exercise;
    }

    public void setExercise(String mExercise)
    {
        _Exercise = mExercise;
    }

    public String getReps()
    {
        return _Reps;
    }

    public void setReps(String mReps)
    {
        _Reps = mReps;
    }

    public String getWeight()
    {
        return _Weight;
    }

    public void setWeight(String mWeight)
    {
        _Weight = mWeight;
    }

// empty constructor
    public myExerciseLog()
    {

    }

    public myExerciseLog(int id, String _Exercise, String _Reps, String _Weight)
    {
        this._ID = id;
        this._Exercise = _Exercise;
        this._Reps = _Reps;
        this._Weight = _Weight;

    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Exercise: "  +_Exercise+ "\n Reps: "  +  _Reps +  "\nWeight: " + _Weight;
    }
}
