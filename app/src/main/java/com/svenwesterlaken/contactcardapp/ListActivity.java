package com.svenwesterlaken.contactcardapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements UserGenerator.RandomUserListener {
    private ListView cl;
    private ArrayList<RandomUserItem> users;
    private RandomUserAdapter rua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        users = new ArrayList<>();
        cl = (ListView) findViewById(R.id.ContactList);
        FloatingActionButton refreshBtn = (FloatingActionButton) findViewById(R.id.refreshBtn);
        rua = new RandomUserAdapter(this, getLayoutInflater(), users);

        cl.setAdapter(rua);
        getRandomUsers();
        rua.notifyDataSetChanged();

        cl.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("JSON", users.get(i).getJson());
                intent.putExtra("ImageURL", users.get(i).getImage());
                startActivity(intent);
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.clear();
                getRandomUsers();
                rua.notifyDataSetChanged();
            }
        });


    }

    public void getRandomUsers() {
        UserGenerator task = new UserGenerator(this);
        String[] urls = new String[] {"https://randomuser.me/api/?results=15&nat=us,fr,gb,nl&exc=login,registered,dob,id"};
        task.execute(urls);
    }

    @Override
    public void onRandomUserAvailable(RandomUserItem i) {
        users.add(i);
        Log.i("ListActivity", i.getName());
        rua.notifyDataSetChanged();
    }
}
