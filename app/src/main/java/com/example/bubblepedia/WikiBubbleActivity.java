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

import Utility.HttpAsyncTask;
import Utility.IDoAsyncAction;


public class WikiBubbleActivity extends AppCompatActivity implements IDoAsyncAction {

    private Integer userId;

    public static final String CONTENT_MESSAGE = "com.example.bubblepedia.content";

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("mytag", "Stopped");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("mytag","PAUSED");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("mytag", "Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_bubble);
    }

    @Override
    protected void onDestroy() {
        Log.v("mytag","Destoryed");
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

    public void gotoSearchableActivity(View view){
        TextView textView = (TextView) findViewById(R.id.bubbleTextview);
        String s = textView.getText().toString();

        if(!s.trim().equals("")){
            Intent intent = new Intent(this,SearchableActivity.class);
            intent.putExtra(CONTENT_MESSAGE, s);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"No Content",Toast.LENGTH_SHORT).show();
        }
    }

    public void postContentWithoutTag(View view){
        //Write to Db
        String postParams = buildServletParams();
        HttpAsyncTask task = new HttpAsyncTask(this);
        task.executeWithNetworkCheck(getResources().getString(R.string.post_wiki_bubble_toDb_servlet_endpoint), "POST", postParams);
        Intent intent = new Intent(this,WikiBubbleActivity.class);
        startActivity(intent);
        finish();
    }

    private String buildServletParams() {
        String postParams = "";
        Integer userId = getUserId();
        TextView textView = (TextView) findViewById(R.id.bubbleTextview);
        String wikiBubbleContent = textView.getText().toString();

        postParams = String.format("userid=%d&content=%s",userId,wikiBubbleContent);
        return postParams;
    }

/*
    @Override
    public String DoBackgroundAction(String buffer) {
        return buffer;
    }
*/

    @Override
    public void DoResult(String doBackgroundString) {
        if(doBackgroundString == null || doBackgroundString.equals("NA")){
            Toast.makeText(this,getResources().getString(R.string.Java_Servlet_Error),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,getResources().getString(R.string.Java_Servlet_Post_Success),Toast.LENGTH_LONG).show();
        }
    }

    public Integer getUserId() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.MY_SHARED_PREFERENCE), MODE_PRIVATE);
        return preferences.getInt(getResources().getString(R.string.USER_ID), 0);
    }
}
