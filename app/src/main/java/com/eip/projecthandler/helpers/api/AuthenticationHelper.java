package com.eip.projecthandler.helpers.api;

import android.content.Context;

import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.listeners.LogInListener;
import com.eip.projecthandler.listeners.LogOutListener;
import com.eip.projecthandler.models.Account;

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
        logInListener.onLogInSuccess(emailAddress, password, "delamerde");
//        NetworkHelper.getInstance(context).requestServer(new NetworkListener() {
//
//            @Override
//            public void onCallSuccess(JSONObject result) {
//                try {
//                    result = new JSONObject("{\"authToken\": \"salut\"}");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    String authToken = result.getString("authToken");
//                    NetworkHelper.getInstance(context).setAuthToken(authToken);
//                    logInListener.onAuthenticationSuccess(emailAddress, password, authToken);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    logInListener.onAuthenticationError(e.getMessage());
//                }
//            }
//
//            @Override
//            public void onCallError(VolleyError error) {
//                logInListener.onAuthenticationError(error.getMessage());
//            }
//
//        }, Request.Method.GET, "https://www.google.ie");
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
