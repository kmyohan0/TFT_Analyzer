package com.example.tfthelper.Model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

public class JSONParser extends AsyncTask<String, Integer, String> {

    private String[] actualURL;
    private String postJson;

    //TODO: Test to see if it is working fine
    @Override
    protected String doInBackground(String... urls) {
        try {
            java.net.URL url = new java.net.URL(urls[0]);
            actualURL = urls;
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                postJson += line;
                Log.d("Looping", "Retrieving Data is Working");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Looping Error", "Async Not working");
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //This is where you handle JSON file.
    //TODO: Use GSON to retreive data that you want
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
