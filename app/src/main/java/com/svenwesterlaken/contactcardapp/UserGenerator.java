package com.svenwesterlaken.contactcardapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sven on 10-3-2017.
 */

public class UserGenerator extends AsyncTask<String, Void, String> {

    private RandomUserListener listener = null;

    public UserGenerator(RandomUserListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        BufferedReader reader = null;
        String urlString = "";
        String response = "";

        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine().toString();
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
        } catch (MalformedURLException e) {
            Log.e("UserAPI", e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("UserAPI", e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            Log.e("UserAPI", e.getLocalizedMessage());
            return null;
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("Connection", e.getLocalizedMessage());
                    return null;
                }
            }
        }

        return response;
    }

    protected void onPostExecute(String response) {
        Log.i("Info", response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray users = jsonObject.getJSONArray("results");

            for(int i=0; i < users.length(); i++) {
                RandomUserItem item = new RandomUserItem();
                String name = users.getJSONObject(i).getJSONObject("name").getString("first");

                item.setName(name);
                listener.onRandomUserAvailable(item);



            }

        } catch (JSONException e) {
            Log.e("JSON", e.getLocalizedMessage());
        }
    }

    public interface RandomUserListener {
        void onRandomUserAvailable(RandomUserItem i);
    }
}
