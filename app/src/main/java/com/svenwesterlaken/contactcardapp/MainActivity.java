package com.svenwesterlaken.contactcardapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        TableLayout content = (TableLayout) findViewById(R.id.userContent);
        ImageView userimg = (ImageView) findViewById(R.id.photoImageView);
        ImageView background = (ImageView) findViewById(R.id.userBackgroundImage);
        JSONObject info;

        Picasso.with(this).load(extras.getString("ImageURL")).into(userimg);
        Picasso.with(this).load("http://lorempixel.com/1578/1048/").error(R.drawable.bridge).into(background);


        try {
            info = new JSONObject(extras.getString("JSON"));
            Iterator<?> keys = info.keys();

            while( keys.hasNext() ) {
                String key = (String)keys.next();
                if ( info.get(key) instanceof JSONObject && !key.equals("picture")) {
                    Log.i("JSON_Key", key);
                    Log.i("JSON", info.get(key).toString());

                    TableRow headerRow = new TableRow(this);
                    TableLayout.LayoutParams lParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                    lParams.setMargins(0, sizeUtil.convertDPtoPX(this, 10), 0, 0);
                    headerRow.setLayoutParams(lParams);

                    TextView headerText = new TextView(this);
                    headerText.setText(key);
                    headerText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    headerText.setAllCaps(true);
                    headerText.setTextColor(Color.parseColor("#1e1e1e"));
                    headerText.setTypeface(Typeface.DEFAULT_BOLD);

                    headerRow.addView(headerText);
                    content.addView(headerRow);

                    Iterator<?> subkeys = ((JSONObject) info.get(key)).keys();
                    JSONObject subinfo = new JSONObject(info.get(key).toString());

                    while( subkeys.hasNext()) {
                        String subkey = (String)subkeys.next();

                        Log.i("JSON_SUBKEY", subkey.toString());

                        TableRow row = new TableRow(this);
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                        String keyText = subkey.substring(0, 1).toUpperCase() + subkey.substring(1) + ": ";
                        String keyvalue = subinfo.get(subkey).toString();
                        String valText = keyvalue.substring(0, 1).toUpperCase() + keyvalue.substring(1);

                        TextView keyTV = new TextView(this);
                        keyTV.setText(keyText);
                        keyTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
                        keyTV.setTypeface(Typeface.DEFAULT_BOLD);


                        TextView valueTV = new TextView(this);
                        valueTV.setText(valText);
                        valueTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f));
                        valueTV.setGravity(Gravity.RIGHT);

                        row.addView(keyTV);
                        row.addView(valueTV);

                        content.addView(row);
                    }


                } else if (!key.equals("picture")) {
                    Log.i("JSON_Key", key + ": " + info.get(key));

                    TableRow row = new TableRow(this);
                    TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0, sizeUtil.convertDPtoPX(this, 5), 0, sizeUtil.convertDPtoPX(this, 2));
                    row.setLayoutParams(params);

                    String keyText = key.substring(0, 1).toUpperCase() + key.substring(1) + ": ";
                    String keyvalue = info.get(key).toString();
                    String valText = keyvalue.substring(0, 1).toUpperCase() + keyvalue.substring(1);

                    TextView keyTV = new TextView(this);
                    keyTV.setText(keyText);
                    keyTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
                    keyTV.setTypeface(Typeface.DEFAULT_BOLD);


                    TextView valueTV = new TextView(this);
                    valueTV.setText(valText);
                    valueTV.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f));
                    valueTV.setGravity(Gravity.RIGHT);

                    row.addView(keyTV);
                    row.addView(valueTV);

                    content.addView(row);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
