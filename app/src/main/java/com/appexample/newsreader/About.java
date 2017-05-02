package com.appexample.newsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView textView1, textView2;
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        Intent intent = getIntent();

        img1 = (ImageView) findViewById(R.id.imageView);
        textView1 = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

        textView1.setText("About Us");
        textView2.setText("This is an Android application that provides you the latest news related to the gaming environment around us....");
    }
}
