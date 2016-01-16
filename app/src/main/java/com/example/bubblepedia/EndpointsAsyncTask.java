package com.example.bubblepedia;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.lenovo.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.Map;

import Utility.ApiMethods;
import Utility.HelperClass;
import Utility.IDoAsyncAction;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context,ApiMethods>, Void, String> {
    private static MyApi myApiService = null;
    protected Map<String,String> params;
    private IDoAsyncAction action;
    private ProgressDialog progressDialog;
    private Context context;

    public EndpointsAsyncTask(Context handle) {
        this.action = (IDoAsyncAction) handle;
        this.context = handle;
    }

    @Override
    protected String doInBackground(Pair<Context,ApiMethods>...pairs) {
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
        } catch (Exception e) {
            Log.v("mytag",e.getMessage());
            return null;
        }
    }

    private String executeMyApiService(ApiMethods apiServiceName, Map<String,String> params) throws Exception {
        String returnValue = "";
        switch (apiServiceName){
            case sayHi:
                returnValue = myApiService.sayHi(params.get("name"), "maj", "ttt").execute().getData();
                break;
            case getWikiOpenSearchResult:
                returnValue = myApiService.getWikiOpenSearchResult(params.get("wikisearch")).execute().getData();
                break;
            case setWikiBubble:
                String tag = params.get("tag");
                if(tag != null && tag != ""){
                    myApiService.setWikiBubble(params.get("userid"),params.get("content"),params.get("tag")).execute().getData();
                }else {
                    myApiService.setWikiBubbleWithoutTag(params.get("userid"),params.get("content")).execute();
                }
                break;
        }
        return returnValue;
    }

    public void executeWithNetworkCheck(ApiMethods apiServiceName ,Map params){
        this.params = params;
        if(HelperClass.IsNetworkConnectionAvailable(this.context)){
            this.execute(new Pair<>(context, apiServiceName));
        }
        else {
            Toast.makeText(context, "No Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        if(s != null){
            // Null is returned only in case of error in the doBackground
            action.DoResult(s);
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.Java_Servlet_Error),Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }
}