package svin.beerbowl.utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by AbstraktenPC on 16-06-2016.
 */
public class NetworkSingleton {
    private static NetworkSingleton ourInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    // TODO: consider using URL class instead of just strings
    // URL's for network communication
    private static String authentication = "http://5.206.195.231:8080/login";

    private NetworkSingleton(Context context) {
        mContext = context;
        mRequestQueue = getmRequestQueue();
    }

    public static synchronized NetworkSingleton getInstance(Context context){
        if(ourInstance == null){
            ourInstance = new NetworkSingleton(context);
        }
        return ourInstance;
    }

    public RequestQueue getmRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void AddToRequestQueue(Request<T> req){
        getmRequestQueue().add(req);
    }

    public static String getAuth() {
        return authentication;
    }

    public static void setAuthentication(String authentication) {
        NetworkSingleton.authentication = authentication;
    }
}
