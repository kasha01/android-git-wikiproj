package Utility;

/**
 * Created by Lenovo on 12/11/2015.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Gets the Votes from Db and Populate Votes Headline Fragment
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    private IDoAsyncAction action;
    private Context context;
    private ProgressDialog progressDialog;

    public HttpAsyncTask(Context handle) {
        this.action = (IDoAsyncAction) handle;
        this.context = handle;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
/*
    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }*/

    @Override
    protected String doInBackground(String... params) {
        char[] buffer = null;
        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(6000);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            buffer = new char[Integer.parseInt(connection.getHeaderField("Content-Length"))];
            int bytesRead = 0;
            while (bytesRead < buffer.length) {
                bytesRead = bytesRead + br.read(buffer, bytesRead, 1);
            }
        } catch (SocketTimeoutException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return action.DoBackgroundAction(new String(buffer));
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        action.DoResult(s);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        progressDialog.dismiss();
    }
}