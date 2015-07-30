package com.eip.projecthandler.listeners;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;

public interface ArrayNetworkListener {

    void onCallSuccess(JSONArray result) throws JSONException;

    void onCallError(VolleyError error);

}
