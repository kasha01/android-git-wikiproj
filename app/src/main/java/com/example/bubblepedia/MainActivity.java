package com.example.bubblepedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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
        /*Map<String,String> m = new HashMap<>();
        m.put("userid","22");
        m.put("content","foooood");
        m.put("tag","");
        m.put("name","Jamoos");*/
        //new EndpointsAsyncTask(this).executeWithNetworkCheck(ApiMethods.setWikiBubble,m);
        //new EndpointsAsyncTaskHelper(this).executeWithNetworkCheck("getWikiOpenSearchResult","panzer");
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        //this.finish();
    }

    @Override
    public void DoResult(String doBackgroundString) {
        Toast.makeText(this, doBackgroundString , Toast.LENGTH_LONG).show();
    }
}