package com.example.bubblepedia;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Lenovo on 12/26/2015.
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}