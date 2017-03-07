package com.babarehner.android.xminder;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mike on 3/6/17.
 */

public class NavItemAdapter extends ArrayAdapter<NavItem> {


    public NavItemAdapter(Activity context, ArrayList<NavItem> navItems) {
        super(context, 0, navItems);
        //mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout
                    .list_item, parent, false);
        }

        NavItem currentNavItem = getItem(position);

        TextView menuItemTextView = (TextView) listItemView.findViewById(R.id.menu_name);
        menuItemTextView.setText(currentNavItem.getMenuItem());
        menuItemTextView.setBackgroundColor(Color.parseColor(currentNavItem.getBackgroundColor()));
        menuItemTextView.setTextColor(Color.parseColor(currentNavItem.getTextColor()));

        // set the theme color for the list
        View textContainer = listItemView.findViewById(R.id.text_container);


        // Return the whole list item layour containung the TextViews and the Image View
        return listItemView;

    }
}
