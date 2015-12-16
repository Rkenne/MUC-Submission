package com.example.robbie.gymapp;

import android.app.Activity;

/**
 * Created by Robbie on 24-Nov-15.
 */
public class rssFeed extends Activity {
    //class to match items fetched from rssfeed to build rssobject. title and link are strings


    private String title,link;
//getters and setters for rss feed - looking for title and link
    public String title(){
        return title;
    }
    public void setTitle(String iTitle){
        this.title = iTitle;
    }
    public String getLinks()
    {
        return link;
    }
    public void setLinks(String iLinks)
    {
        this.link = iLinks;
    }

    @Override
    public String toString() {
        //returns information via toString method.
        // TODO Auto-generated method stub
        return "Title: "  +title+ "\nLink: "  +  link;
    }
}
