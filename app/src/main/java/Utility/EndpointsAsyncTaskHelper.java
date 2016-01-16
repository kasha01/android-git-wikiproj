package Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Pair;
import android.widget.Toast;
import com.example.bubblepedia.EndpointsAsyncTask;
import com.example.bubblepedia.R;

import org.json.JSONObject;

/**
 * Gets the Votes from Db and Populate Votes Headline Fragment
 */
public class EndpointsAsyncTaskHelper {/*

    private IDoAsyncAction action;
    private ProgressDialog progressDialog;
    private Context context;

    public EndpointsAsyncTaskHelper(Context handle) {
        this.action = (IDoAsyncAction) handle;
        this.context = handle;
    }

    public void executeWithNetworkCheck(String apiServiceName ,JSONObject params){
        if(HelperClass.IsNetworkConnectionAvailable(this.context)){
            super.params = params;
            super.execute(new Pair<Context, String>(context, apiServiceName));
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
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }*/
}