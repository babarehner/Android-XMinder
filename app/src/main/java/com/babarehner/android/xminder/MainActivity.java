package com.babarehner.android.xminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

// test for changes in git
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_list);

        ArrayList<NavItem> navItems = new ArrayList<NavItem>();
        navItems.add(new NavItem("Cardio", "#FFFFFF", "#F50057" ));
        navItems.add(new NavItem("Strength", "#FFFFFF", "#fd8e09"));


        // WordAdapter adapter = new WordAdapter(this, words, R.color.category_cardio_rehab);
        NavItemAdapter adapter = new NavItemAdapter(this, navItems);

        ListView listView = (ListView) findViewById(R.id.nav_list);

        listView.setAdapter (adapter);
    }
}
