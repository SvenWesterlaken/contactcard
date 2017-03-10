package com.svenwesterlaken.contactcardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ListActivity extends AppCompatActivity implements UserGenerator.RandomUserListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getRandomUsers();
    }

    public void getRandomUsers() {
        UserGenerator task = new UserGenerator(this);
        String[] urls = new String[] {"https://randomuser.me/api/?results=15"};
        task.execute(urls);
    }

    @Override
    public void onRandomUserAvailable(RandomUserItem i) {
        Log.i("ListActivity", i.getName());
    }
}
