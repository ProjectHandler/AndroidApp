package com.eip.projecthandler.helpers.api;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.listeners.LogInListener;
import com.eip.projecthandler.listeners.LogOutListener;
import com.eip.projecthandler.listeners.ObejctNetworkListener;
import com.eip.projecthandler.models.Account;

import org.json.JSONException;
import org.json.JSONObject;

public final class AuthenticationHelper {

    /**
     * Logs in the user.
     * Will call back LogInListener.onLogInSuccess or LogInListener.onLogInError.
     *
     * @param context       The context.
     * @param logInListener The log in listener.
     * @param emailAddress  The user email address.
     * @param password      The user password.
     */
    public static void authenticate(final Context context,
                                    final LogInListener logInListener,
                                    final String emailAddress,
                                    final String password) {
        NetworkHelper.getInstance(context).objectRequestServer(new ObejctNetworkListener() {

            @Override
            public void onCallSuccess(JSONObject result) {
                try {
                    String authToken = result.getString("token");
                    NetworkHelper.getInstance(context).setAuthToken(authToken);
                    logInListener.onAuthenticationSuccess(emailAddress, password, authToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                    logInListener.onAuthenticationError(e.getMessage());
                }
            }

            @Override
            public void onCallError(VolleyError error) {
                Integer code = error.networkResponse.statusCode;
                logInListener.onAuthenticationError(code.toString());
            }

        }, Request.Method.GET, ApiRoutes.authentication(emailAddress, password, (Activity) context), null);
    }

    /**
     * Logs out the user.
     * Will call back LogOutListener.onLogOutSuccess or LogOutListener.onLogOutError.
     *
     * @param context        The context.
     * @param logOutListener The log out listener.
     */
    public static void logOut(Context context, LogOutListener logOutListener) {
        Account account = AccountHelper.getAccount(context);
        if (account != null) AccountHelper.removeAccount(context, account, logOutListener);
    }

}
