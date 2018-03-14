package com.babarehner.android.xminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/***
/**
 * TODO add database for strength training and Cardio training
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_list);

        ArrayList<NavItem> navItems = new ArrayList<NavItem>();
        navItems.add(new NavItem("Cardio", "#FFFFFF", "#F50057" ));
        navItems.add(new NavItem("Strength", "#FFFFFF", "#fd8e09"));

        //TODO add two more navigation items for "Flexibity" and "Balance"


        // WordAdapter adapter = new WordAdapter(this, words, R.color.category_cardio_rehab);
        NavItemAdapter adapter = new NavItemAdapter(this, navItems);

        ListView menuListView = (ListView) findViewById(R.id.nav_list);

        // populate listview with items from the ArrayAdapter- NavItemAdapter
        menuListView.setAdapter (adapter);


        // Set an item click listener on menuListView.
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                if (pos == 0) { // first item in arraylist not yet implemented
                }
                if (pos == 1) {
                    Intent intent = new Intent(MainActivity.this,
                            StrengthActivity.class);
                    startActivity(intent);
                }
            }
        });



    }
}

