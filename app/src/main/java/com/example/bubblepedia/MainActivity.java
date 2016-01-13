package com.example.bubblepedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import Utility.EndpointsAsyncTaskHelper;
import Utility.IDoAsyncAction;

public class MainActivity extends Activity implements IDoAsyncAction {

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
        new EndpointsAsyncTaskHelper(this).executeWithNetworkCheck("sayHi","Jamica");
        /*Intent intent = new Intent(this,WikiBubbleActivity.class);
        startActivity(intent);
        this.finish();*/
    }

    @Override
    public void DoResult(String doBackgroundString) {
        Toast.makeText(this, doBackgroundString + "_From override", Toast.LENGTH_LONG).show();
    }
}