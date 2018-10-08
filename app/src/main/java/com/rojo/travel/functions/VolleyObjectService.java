package com.rojo.travel.functions;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by limadawai on 11/1/17.
 */

public class VolleyObjectService {

    VolleyObjectResult volleyObjectResult = null;
    VolleyArrayResult volleyArrayResult = null;
    Context context;

    public VolleyObjectService(VolleyObjectResult volleyObjectResult, Context context) {
        this.volleyObjectResult = volleyObjectResult;
        this.context = context;
    }

    public void postJsonObject(final String requestType, String url, JSONObject params){
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest objectRequest = new JsonObjectRequest(url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    if(volleyObjectResult != null)
                        volleyObjectResult.resSuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(volleyObjectResult != null)
                        volleyObjectResult.resError(requestType, error);
                }
            });

            objectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(objectRequest);
        } catch (Exception e){

        }
    }

    public void getJsonObject(final String requestType, String url){
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(volleyObjectResult != null)
                        volleyObjectResult.resSuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(volleyObjectResult != null)
                        volleyObjectResult.resError(requestType, error);
                }
            });
            jsonObj.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObj);

        }catch(Exception e){

        }
    }
}
