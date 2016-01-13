package com.example.bubblepedia;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.example.lenovo.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context,String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context = null;
    protected String params;

    @Override
    protected String doInBackground(Pair<Context,String>...pairs) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    /* options for running agains Live App Engine
                    https://mytestproject-1812.appspot.com/_ah/api/
                    options for running against local devappserver
                    - 10.0.2.2 is localhost's IP address in Android emulator
                    http://10.0.2.2:8080/_ah/api/
                    - turn off compression when running against local devappserver  */

                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    //.setRootUrl("https://mytestproject-1812.appspot.com/_ah/api/")

                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        context = pairs[0].first;

        try {
            return executeMyApiService(pairs[0].second,params);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String executeMyApiService(String apiServiceName, String params) throws IOException {
        String returnValue = "";
        switch (apiServiceName){
            case "sayHi":
                returnValue = myApiService.sayHi(params.toString()).execute().getData();
                break;
            case "sayWiki":
                returnValue = myApiService.sayWiki().execute().getData();
                break;
        }
        return returnValue;
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}