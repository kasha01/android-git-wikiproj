package Utility;

/**
 * Created by Lenovo on 12/11/2015.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bubblepedia.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

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


    public void executeWithNetworkCheck(String...params){
        if(HelperClass.IsNetworkConnectionAvailable(this.context)){
            this.execute(params);
        }
        else {
            Toast.makeText(context,"No Network Connection",Toast.LENGTH_LONG).show();
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
/*
    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }*/

    /**
     * Parameters: 1:URL, 2:Request Method, 3: Post Parameters (if POST)
     * */
    @Override
    protected String doInBackground(String... params) {
        char[] buffer = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            if(params[1].equals("POST")){
                connection.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                os.write(params[2]);
                os.flush();
                os.close();
            }
            connection.setRequestMethod(params[1]);
            connection.setConnectTimeout(6000);

            if(!connection.getHeaderField("Status").equals("200")){
                return null;
            }

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
        //return action.DoBackgroundAction(new String(buffer));
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s != null){
            // Null is returned only in case of error in the doBackground
            action.DoResult(s);
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.Java_Servlet_Error),Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        progressDialog.dismiss();
    }
}