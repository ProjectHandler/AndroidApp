package com.eip.projecthandler.listeners;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface ObejctNetworkListener {

    void onCallSuccess(JSONObject result) throws JSONException;

    void onCallError(VolleyError error);

}
