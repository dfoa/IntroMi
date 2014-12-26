package com.ad.Intromi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       System.out.println("Android Activity jus fireed");
        TextView textview = new TextView(this);
        textview.setText("This is Android tab");
        setContentView(textview);
    }
}