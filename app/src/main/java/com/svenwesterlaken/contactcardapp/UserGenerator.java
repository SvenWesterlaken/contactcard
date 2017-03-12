package com.svenwesterlaken.contactcardapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

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
        BufferedReader reader = null;
        String response = "";

        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();

            if (!(connection instanceof HttpURLConnection)) {
                return null;
            }

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

        if(response == null || response == "") {
            Log.e("UserAPI", "onPostExecute kreeg een lege response!");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray users = jsonObject.getJSONArray("results");

            for(int i=0; i < users.length(); i++) {
                RandomUserItem item = new RandomUserItem();

                String firstname = users.getJSONObject(i).getJSONObject("name").getString("first");
                String lastname = users.getJSONObject(i).getJSONObject("name").getString("last");

                String name = firstname.substring(0, 1).toUpperCase() + firstname.substring(1) + " " + lastname.substring(0, 1).toUpperCase() + lastname.substring(1);
                String imageURL = users.getJSONObject(i).getJSONObject("picture").getString("large");
                String user = users.getJSONObject(i).toString();

                item.setName(name);
                item.setImage(imageURL);
                item.setJson(user);
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
