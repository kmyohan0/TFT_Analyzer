package com.example.tfthelper.Model.Parsers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tfthelper.Model.Dto.MatchIds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class MatchesAsync extends AsyncTask<String, Integer, String> {

    private String[] actualURL;
    private String postJson = "";
    public String[] matchLists;

    public MatchIds matchIds;

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... urls) {
        try {
            java.net.URL url = new java.net.URL(urls[0]);
            actualURL = urls;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d("doInBackground", line);
                postJson += line;
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        matchLists = gson.fromJson(postJson, String[].class);
        matchIds = new MatchIds(matchLists);
        Log.d("delegateNullReference", String.valueOf(delegate == null));
        delegate.matchListsResponse(matchIds);
    }
}

