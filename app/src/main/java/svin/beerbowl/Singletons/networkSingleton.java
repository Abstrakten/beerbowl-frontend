package svin.beerbowl.singletons;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by AbstraktenPC on 16-06-2016.
 */
public class networkSingleton {
    private static networkSingleton ourInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private networkSingleton(Context context) {
        mContext = context;
        mRequestQueue = getmRequestQueue();
    }

    public static synchronized networkSingleton getInstance(Context context){
        if(ourInstance == null){
            ourInstance = new networkSingleton(context);
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
}
