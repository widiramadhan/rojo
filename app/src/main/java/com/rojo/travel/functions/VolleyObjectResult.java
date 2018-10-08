package com.rojo.travel.functions;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by limadawai on 11/1/17.
 */

public interface VolleyObjectResult {

    public void resSuccess(String requestType, JSONObject response);
    public void resError(String requestType, VolleyError error);

}
