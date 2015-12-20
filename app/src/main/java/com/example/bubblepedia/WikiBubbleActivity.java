package com.example.bubblepedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Utility.HttpAsyncTask;
import Utility.IDoAsyncAction;


public class WikiBubbleActivity extends ActionBarActivity implements IDoAsyncAction<String> {

    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_bubble);
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

    public void tagMe(View view){
        TextView textView = (TextView) findViewById(R.id.bubbleTextview);
        String s = textView.getText().toString();

        if(!s.trim().equals("")){
            Intent intent = new Intent(this,SearchableActivity.class);
            intent.putExtra("content", textView.getText());
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"No Content",Toast.LENGTH_SHORT).show();
        }
    }

    public void postContent(View view){
        //Write to Db
        String postParams = buildServletParams();
        HttpAsyncTask task = new HttpAsyncTask(this);
        task.executeWithNetworkCheck(getResources().getString(R.string.post_wiki_bubble_toDb_servlet_endpoint), "POST", postParams);
    }

    private String buildServletParams() {
        String postParams = "";
        Integer userId = getUserId();
        TextView textView = (TextView) findViewById(R.id.bubbleTextview);
        String wikiBubbleContent = textView.getText().toString();

        postParams = String.format("userid=%d&content=%s",userId,wikiBubbleContent);
        return postParams;
    }

    @Override
    public String DoBackgroundAction(String buffer) {
        return buffer;
    }

    @Override
    public void DoResult(String doBackgroundString) {
        if(doBackgroundString.equals("NA")){
            Toast.makeText(this,getResources().getString(R.string.Java_Servlet_Error),Toast.LENGTH_LONG).show();
        }
    }

    public Integer getUserId() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.MY_SHARED_PREFERENCE), MODE_PRIVATE);
        return preferences.getInt(getResources().getString(R.string.USER_ID), 0);
    }
}
