package com.example.bubblepedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), SearchableActivity.class);
                startActivity(intent);
            }
        }, 3000);*/
    }

    public void clickme(View view){
        Intent intent = new Intent(this,WikiBubbleActivity.class);
        startActivity(intent);
    }
}