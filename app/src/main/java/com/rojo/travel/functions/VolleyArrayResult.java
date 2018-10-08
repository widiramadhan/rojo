package com.rojo.travel.functions;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Created by limadawai on 11/1/17.
 */

public interface VolleyArrayResult {

    public void resSuccess(String requestType, JSONArray response);
    public void resError(String requestType, VolleyError error);

}
