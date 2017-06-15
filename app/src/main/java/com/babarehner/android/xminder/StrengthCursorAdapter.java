package com.babarehner.android.xminder;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by mike on 6/15/17.
 */

public class StrengthCursorAdapter extends CursorAdapter {

    public StrengthCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View v, Context context, Cursor cursor) {

    }
}
