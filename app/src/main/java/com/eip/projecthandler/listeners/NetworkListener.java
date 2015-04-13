package com.eip.projecthandler.listeners;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface NetworkListener {

    void onCallSuccess(JSONObject result);

    void onCallError(VolleyError error);

}
