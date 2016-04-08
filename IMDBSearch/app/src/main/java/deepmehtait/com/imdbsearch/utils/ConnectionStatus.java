package deepmehtait.com.imdbsearch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Deep on 04-Apr-16.
 * Used to check internet connectivity
 */
// Centralized Network Status Checker
public class ConnectionStatus {
    private static ConnectionStatus instance=new ConnectionStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected=false;
    public static ConnectionStatus getInstance(Context ctx){
        context = ctx.getApplicationContext();
        return instance;
    }
    public boolean isOnline(){
        try{
            connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        }
        catch(Exception e){
            Log.v("AppStatus Class", e.toString());

        }
        return connected;
    }
}
