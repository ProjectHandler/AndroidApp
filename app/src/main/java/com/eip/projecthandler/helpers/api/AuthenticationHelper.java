package com.eip.projecthandler.helpers.api;

import android.content.Context;
import android.util.Log;

import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.listeners.LogInListener;
import com.eip.projecthandler.listeners.LogOutListener;
import com.eip.projecthandler.models.Account;
import com.eip.projecthandler.models.Token;

public final class AuthenticationHelper {

    private static final MyAsyncTask asyncTask = new MyAsyncTask();
    private static String emailAddress;
    private static String password;
    private static LogInListener logInListener;

    /**
     * Logs in the user.
     * Will call back LogInListener.onLogInSuccess or LogInListener.onLogInError.
     *
     * @param context       The context.
     * @param logInListener The log in listener.
     * @param emailAddress  The user email address.
     * @param password      The user password.
     */

    public AuthenticationHelper(final LogInListener logInListener,
                                final String emailAddress,
                                final String password) {
        this.asyncTask.delegate = this;
        this.logInListener = logInListener;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public static void authenticate() {
        Token t = new Token();
        try {
            asyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void processFinish(String output) {
        Log.e("AuthenticationHelper", "result of get: " + output);
        logInListener.onLogInSuccess(emailAddress, password, output);
    }
}
