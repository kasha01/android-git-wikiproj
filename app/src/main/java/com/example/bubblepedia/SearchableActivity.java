package com.example.bubblepedia;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utility.ApiMethods;
import Utility.IDoAsyncAction;
import Utility.WikiSearchResultTextView;

public class SearchableActivity extends AppCompatActivity implements IDoAsyncAction {

    private ArrayAdapter adapter = null;
    private List<String> wikiSearchResult = new ArrayList<String>();
    private List<String> list = new ArrayList<>();
    private SearchView searchView = null;
    private boolean IsPost = false;
    private EditText txtBubbleSubject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        txtBubbleSubject = (EditText) findViewById(R.id.txtBubbleSubject);
        txtBubbleSubject.setVisibility(View.VISIBLE);

        list.clear();
        adapter = new ArrayAdapter<String>(this, R.layout.wikisearch_listview_row, wikiSearchResult);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WikiSearchResultTextView wikiView = (WikiSearchResultTextView) view;
                wikiView.setToggle(!wikiView.isToggle());
                if (wikiView.isToggle()) {
                    wikiView.setBackgroundColor(getResources().getColor(R.color.wikisearch_textview_backgroundcolor_1));
                    list.add(wikiView.getText().toString());
                } else {
                    wikiView.setBackgroundColor(0);
                    list.remove(wikiView.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.v("mytag", "NewIntent");
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent.ACTION_SEARCH.equals(Intent.ACTION_SEARCH)) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            try {
                doSearch(query);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void doSearch(String query) throws UnsupportedEncodingException {
        if (query != null && query != "") {
            IsPost = false;
            txtBubbleSubject.setVisibility(View.INVISIBLE);
            query = URLEncoder.encode(query, "UTF-8");
            if (query != null && query != "") {
                Map<String, String> m = new HashMap<>();
                m.put("wikisearch", query);
                new EndpointsAsyncTask(this).executeWithNetworkCheck(ApiMethods.getWikiOpenSearchResult, m);
                saveQueryForSuggestion(query);
            }
        }
    }

    private void saveQueryForSuggestion(String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchable, menu);

        //Before we can use the SearchView: we need to associate the Search Widget with the Searchable Configuration
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(false);//senuWolf
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        //searchView.requestFocus();
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

    @Override
    public void DoResult(String doBackgroundString) {
        Log.v("mytag", "Searchable do result");

        if (IsPost) {
            Toast.makeText(this, doBackgroundString, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, WikiBubbleActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        adapter.clear();
        list.clear();
        try {
            JSONArray jarray = new JSONArray(doBackgroundString);
            //String title = jarray.getString(0).toString();
            JSONArray jarraySearch = new JSONArray(jarray.get(1).toString());
            int l = jarraySearch.length();
            for (int i = 0; i < l; i++) {
                wikiSearchResult.add(jarraySearch.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        //searchView.setQuery("", false);
        searchView.setIconified(true);
        searchView.clearFocus();
    }

    public void postContentWithTag(View view) {
        String wikiBubbleContent = getIntent().getStringExtra(WikiBubbleActivity.CONTENT_MESSAGE);
        if (wikiBubbleContent == null || wikiBubbleContent.equals("")) {
            Toast.makeText(this, "No Content to Post",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> m = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        for (String s : list) {
            jsonArray.put(s);
        }
        Integer userId = 4;
        m.put("userid", userId.toString());
        m.put("content", wikiBubbleContent);
        m.put("tag", jsonArray.toString());
        new EndpointsAsyncTask(this).executeWithNetworkCheck(ApiMethods.setWikiBubble, m);
        IsPost = true;
    }

    public void clearSearchHistory(MenuItem menuItem) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.clearHistory();
    }
}