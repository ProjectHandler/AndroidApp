package com.eip.projecthandler.helpers.account;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.text.TextUtils;

import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.AuthenticatorConstants;
import com.eip.projecthandler.listeners.LogOutListener;
import com.eip.projecthandler.models.Account;

import java.io.IOException;

public class AccountHelper {

    /**
     * Returns the account already created.
     *
     * @param context The context.
     * @return The account already created. If no accounts were found, it returns null.
     */
    public static Account getAccount(Context context) {
        AccountManager accountManager = AccountManager.get(context);

        android.accounts.Account[] accounts = accountManager
                .getAccountsByType(context.getString(R.string.app_package_name));
        if (accounts == null || accounts.length <= 0 || accounts[0] == null) return null;

        Account account = new Account(accounts[0].name, accounts[0].type);

        String emailAddress = account.name;
        String password = accountManager.getPassword(account);
        if (TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)) return null;

        account.setPassword(password);

        return account;
    }

    /**
     * Sets the password to the account.
     *
     * @param context  The context.
     * @param account  The account to set the password.
     * @param password The password.
     */
    public static void setPassword(Context context,
                                   android.accounts.Account account,
                                   String password) {
        AccountManager accountManager = AccountManager.get(context);
        accountManager.setPassword(account, password);
    }

    /**
     * Adds a new account.
     *
     * @param context The context.
     * @param account The account to add.
     */
    public static void addAccount(Context context, Account account, String token) {
        AccountManager accountManager = AccountManager.get(context);
        accountManager.addAccountExplicitly(account, account.getPassword(), null);
        //accountManager.setAuthToken(account, authTokenType, authToken);
        accountManager.setAuthToken(account, AuthenticatorConstants.AUTH_TOKEN_TYPE, token);
    }

    /**
     * Removes the account.
     *
     * @param context        The context.
     * @param account        The account to remove.
     * @param logOutListener The Listener to call back the result.
     */
    public static void removeAccount(Context context, Account account, final LogOutListener logOutListener) {
        AccountManager accountManager = AccountManager.get(context);
        accountManager.removeAccount(account, new AccountManagerCallback<Boolean>() {

            @Override
            public void run(AccountManagerFuture<Boolean> future) {
                try {
                    boolean isAccountRemoved = future.getResult();
                    if (isAccountRemoved) logOutListener.onLogOutSuccess();
                    else logOutListener.onLogOutError("Error while removing account.");
                } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                    e.printStackTrace();
                    logOutListener.onLogOutError(e.getMessage());
                }
            }

        }, null);
    }

    /**
     * Determines if an account already exists.
     *
     * @param context The context.
     * @return true if an account already exists, false otherwise.
     */
    public static boolean hasAccount(Context context) {
        AccountManager accountManager = AccountManager.get(context);

        android.accounts.Account[] accounts = accountManager
                .getAccountsByType(context.getString(R.string.app_package_name));
        if (accounts == null || accounts.length <= 0 || accounts[0] == null) return false;

        String emailAddress = accounts[0].name;
        String password = accountManager.getPassword(accounts[0]);
        return !(TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password));
    }

}
