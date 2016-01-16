package com.example.bubblepedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import Utility.ApiMethods;
import Utility.EndpointsAsyncTaskHelper;
import Utility.HttpAsyncTask;
import Utility.IDoAsyncAction;
import Utility.MasterParam;


public class WikiBubbleActivity extends AppCompatActivity implements IDoAsyncAction {

    public static final String CONTENT_MESSAGE = "com.example.bubblepedia.content";

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("mytag", "Stopped");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("mytag", "PAUSED");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("mytag", "Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_bubble);
    }

    @Override
    protected void onDestroy() {
        Log.v("mytag", "Destoryed");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wiki_bubble, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoSearchableActivity(View view) {
        TextView textView = (TextView) findViewById(R.id.bubbleTextview);
        String s = textView.getText().toString();

        if (!s.trim().equals("")) {
            Intent intent = new Intent(this, SearchableActivity.class);
            intent.putExtra(CONTENT_MESSAGE, s);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Content", Toast.LENGTH_SHORT).show();
        }
    }

    public void postContentWithoutTag(View view) {
        String userId = getUserId();
        TextView textView = (TextView) findViewById(R.id.bubbleTextview);
        String wikiBubbleContent = textView.getText().toString();
        if (wikiBubbleContent != null && wikiBubbleContent != "") {
            Map<String, String> m = new HashMap<>();
            m.put("userid", userId);
            m.put("content", wikiBubbleContent);
            new EndpointsAsyncTask(this).executeWithNetworkCheck(ApiMethods.setWikiBubble, m);
        }
        else {
            Toast.makeText(this,"No Content!",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void DoResult(String doBackgroundString) {
        Toast.makeText(this, getResources().getString(R.string.Java_Servlet_Post_Success), Toast.LENGTH_LONG).show();
    }

    public String getUserId() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.MY_SHARED_PREFERENCE), MODE_PRIVATE);
        return preferences.getString(getResources().getString(R.string.USER_ID), "0");
    }
}
