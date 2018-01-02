package app.nmn.com.testapplication.rest;

import com.android.volley.DefaultRetryPolicy;

import java.util.concurrent.TimeUnit;

import app.nmn.com.testapplication.helper.VolleyMultipartRequest;

/**
 * Created by User-PC on 1/2/2018.
 */

public class API {

    public static String PERSONLIST = "http://www.mocky.io/v2/5a488f243000004c15c3c57e";


    public static void defaultPolicy(VolleyMultipartRequest volleyMultipartRequest){
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(30),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }

}
