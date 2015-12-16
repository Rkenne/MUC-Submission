package com.example.robbie.gymapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Robbie on 08-Dec-15.
 */
public class feedLoader extends AppCompatActivity {
/*Class to load the rssfeed and bind it to a listview. variables used to fetchUrl, pass into a string
via urlToParse. rssStream holds the List of the rssFeed, while rssObject is used to get the information via the rssFeed class
getters and setters. rssList is a ListView and text is used for the XMLPullParser operation. mContext is used as the ArrayAdapter
needed a context passed through it.
 */

    private String urlToParse;
    private String urlFetch = "http://www.bodybuilding.com/rss/articles"; // stick in url for rss here
    private List<rssFeed> rssStream;
    private rssFeed rssObject;
    private ListView rssList;
    private String text;
    private Context mContext;
//suppressLint required in loading feed.
    @SuppressLint("Newapi")
    protected void onCreate(Bundle savedInstanceState) {
        //oncreate, sets the content view and uses threadpolicy to load feed. In try Catch statement
        //methods are used to grab the feed and parse it, then creating the listview
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rssfeed);
        mContext = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent test = getIntent();
        try {
            urlToParse = sourceListingString(urlFetch);
            rssStream = parseRSS(urlToParse);
            createListForRss();
        } catch (IOException e) {
            e.printStackTrace();
        }



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


    private static String sourceListingString(String urlString)throws IOException
    {
        //fetches the rssfeed from the internet, passing the url as a string. returns rssfeed as a string.
        String result = "";
        InputStream anInStream = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        // Check that the connection can be opened
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try
        {
            // Open connection
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            // Check that connection is Ok
            if (response == HttpURLConnection.HTTP_OK)
            {
                // Connection is Ok so open a reader
                anInStream = httpConn.getInputStream();
                InputStreamReader in= new InputStreamReader(anInStream);
                BufferedReader bin= new BufferedReader(in);

                // Read in the data from the XML stream
                bin.readLine(); // Throw away the header
                String line = new String();
                while (( (line = bin.readLine())) != null)
                {
                    result = result + "\n" + line;
                }
            }
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");
        }

        // Return result as a string for further processing
        return result;

    }


        private List parseRSS(String data) {
            //passes result from sourceListingString and uses XMLPullParser to parse the data, returning the information as a list of
            //rssfeed
            try{

                rssStream = new ArrayList<rssFeed>();
                XmlPullParserFactory rssFactory = XmlPullParserFactory.newInstance();
                rssFactory.setNamespaceAware(true);
                XmlPullParser rssPull = rssFactory.newPullParser();
                rssPull.setInput(new StringReader(data));


                int eventType = rssPull.getEventType();


                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    String tagName = rssPull.getName();
                    switch (eventType) {
                        //get the start tag

                        case XmlPullParser.START_TAG:
                            if (tagName.equalsIgnoreCase("title")) {
                                rssObject = new rssFeed();
                            }
                            break;


                        case XmlPullParser.TEXT:
                            text = rssPull.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagName.equalsIgnoreCase("title")) {
                                rssStream.add(rssObject);
                                rssObject.setTitle(text);
                                Log.e("MyTag", "Title is:" + text);
                            } else if (tagName.equalsIgnoreCase("link")) {
                                rssObject.setLinks(text);
                                Log.e("MyTag", "link is:" + text);
                            }
                            break;


                    }
                    eventType = rssPull.next();
                } // end of while
            }
            catch (XmlPullParserException ae1)
            {
                Log.e("MyTag","Parsing error" + ae1.toString());
            }
            catch (IOException ae1)
            {
                Log.e("MyTag","IO error during parsing");
            }
            return rssStream;
        }

        public void createListForRss() {
            //uses rssStream list and array adapter to bind to listview, displaying to user.
            rssList = (ListView) findViewById(R.id.rssLister);
            ArrayAdapter<rssFeed> adapter = new ArrayAdapter<rssFeed>
                    (mContext, android.R.layout.simple_list_item_1, rssStream);
            rssList.setAdapter(adapter);
        }

}
