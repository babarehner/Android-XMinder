package com.babarehner.android.xminder;

/**
 * Created by mike on 3/6/17.
 */

public class NavItem {

    private String mMenuItem;
    private String mTextColor;
    private String mBackgroundColor;

    public NavItem( String m, String textColor, String backgroundColor){
        mMenuItem = m;
        mTextColor = textColor;
        mBackgroundColor = backgroundColor;
    }

    public String getMenuItem(){
        return mMenuItem;
    }

    public String getTextColor() { return mTextColor; }

    public String getBackgroundColor() { return mBackgroundColor;}
}
