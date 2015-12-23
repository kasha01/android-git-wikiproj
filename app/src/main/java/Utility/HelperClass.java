package Utility;

/**
 * Created by Lenovo on 12/11/2015.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.bubblepedia.R;

public class HelperClass {

    public static boolean IsNetworkConnectionAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static boolean IsNullOrWhiteSpace(String s){
        if(s!=null && s.trim() != ""){
            return false;
        }
        return true;
    }
}
