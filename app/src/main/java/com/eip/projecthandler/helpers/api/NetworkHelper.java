package com.eip.projecthandler.helpers.api;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eip.projecthandler.exceptions.AuthenticationException;
import com.eip.projecthandler.listeners.NetworkListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkHelper {

    private static NetworkHelper instance;
    private final RequestQueue mRequestQueue;
    private String mAuthToken;

    private NetworkHelper(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Get the NetworkHelper instance.
     *
     * @param context The context.
     * @return The NetworkHelper instance.
     */
    public static NetworkHelper getInstance(Context context) {
        if (instance == null) instance = new NetworkHelper(context);
        return instance;
    }

    /**
     * Sets the authentication token that will be used to request the server.
     *
     * @param authToken The authentication token generated by the server.
     */
    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    /**
     * Calls the server.
     * Will call back LogInListener.onAuthenticateSuccess
     * or LogInListener.onAuthenticateError.
     *
     * @param networkListener The network listener.
     * @param method          The method used. Must be like Request.Method.?
     * @param url             The url.
     * @throws AuthenticationException
     */
    public void requestServer(final NetworkListener networkListener, int method, String url) {
        if (!TextUtils.isEmpty(mAuthToken)) {
            requestServerWithToken(networkListener, method, url);
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(method, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (networkListener != null) networkListener.onCallSuccess(response);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (networkListener != null) networkListener.onCallError(error);
                    }

                });
        mRequestQueue.add(request);
    }

    /**
     * Calls the server with the user authentication token.
     * Will call back LogInListener.onAuthenticateSuccess
     * or LogInListener.onAuthenticateError.
     *
     * @param networkListener The network listener.
     * @param method          The method used. Must be like Request.Method.?
     * @param url             The url.
     * @throws AuthenticationException
     */
    private void requestServerWithToken(final NetworkListener networkListener,
                                        int method,
                                        String url) {
        JsonObjectRequest request = new JsonObjectRequest(method, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (networkListener != null) networkListener.onCallSuccess(response);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (networkListener != null) networkListener.onCallError(error);
                    }

                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authentication", mAuthToken);
                return params;
            }

        };
        mRequestQueue.add(request);
    }

}
