/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Lenovo.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Lenovo.example.com",
                ownerName = "backend.myapplication.Lenovo.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {
    private MyBean response;

    public MyEndpoint() {
        response = new MyBean();
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) throws Exception {
        response.setData("Hi," + name);
        return response;
    }

    @ApiMethod(name = "getWikiOpenSearchResult")
    public MyBean GetWikiOpenSearchResult(@Named("wikisearch") String wikisearch) throws Exception {
        String line = "";
        String qurl = "https://en.wikipedia.org/w/api.php?action=opensearch&search=" + wikisearch;

        URL url = null;
        URLConnection connection = null;
        BufferedReader in = null;
        try {
            url = new URL(qurl);
            connection = url.openConnection();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result;

            while ((result = in.readLine()) != null) {
                line = line + result;
            }
        } finally {
            in.close();
        }
        response.setData(line);
        return response;
    }

    @ApiMethod(name = "setWikiBubble")
    public MyBean setWikiBubble(@Named("userid") String userid, @Named("content") String content, @Named("tag") String tag) throws Exception {

        Repository repo = new Repository();
        int tagid = repo.SaveWikiBubbleToDb(Integer.parseInt(userid), content);

        if (tagid > -1) {
            // Insert selected Tags..Tags are JSON Array
            JSONArray jsonArray = new JSONArray(tag);
            for (int i = 0; i < jsonArray.length(); i++) {
                repo.SaveWikiBubbleTags(tagid, jsonArray.getString(i));
            }
        }
        response.setData("We got your think bubble");
        return response;
    }

    @ApiMethod(name = "setWikiBubbleWithoutTag")
    public MyBean setWikiBubbleWithoutTag(@Named("userid") String userid, @Named("content") String content) throws Exception {
        Repository repo = new Repository();
        repo.SaveWikiBubbleToDb(Integer.parseInt(userid), content);

        response.setData("We got your think bubble");
        return response;
    }
}
