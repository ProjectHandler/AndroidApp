package com.eip.projecthandler.activities;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.AuthenticatorConstants;
import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.helpers.api.AuthenticationHelper;
import com.eip.projecthandler.listeners.LogInListener;
import com.eip.projecthandler.models.Account;

public class LogInActivity extends AccountAuthenticatorActivity implements LogInListener {

    private View mFormView;
    private View mProgressBar;
    private Account mAccount;
    private boolean mHasAccount = true;

    /**
     * Starts LogInActivity.
     *
     * @param context The context of the Activity that calls this method.
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, LogInActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mFormView = findViewById(R.id.activity_authenticator_form);
        mProgressBar = findViewById(R.id.activity_authenticator_progress);

        autoAuthenticate();
    }

    @Override
    public void onLogInSuccess(String emailAddress, String password, String authToken) {
        if (mHasAccount) AccountHelper.setPassword(this, mAccount, password);
        else {
            String accountType = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
            String authTokenType = getIntent()
                    .getStringExtra(AuthenticatorConstants.AUTH_TOKEN_TYPE);

            if (accountType == null) accountType = getString(R.string.app_package_name);

            Bundle data = new Bundle();
            data.putString(AccountManager.KEY_ACCOUNT_NAME, emailAddress);
            data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
            data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            data.putString(AuthenticatorConstants.AUTH_TOKEN_TYPE, authTokenType);
            data.putString(AuthenticatorConstants.ACCOUNT_PASSWORD, password);


            Log.d("LogInActivity", "authToken: " + authToken);

            Intent intent = new Intent();
            intent.putExtras(data);

            mAccount = new Account(emailAddress, accountType);
            mAccount.setPassword(password);
            AccountHelper.addAccount(this, mAccount);
        }

        HomeActivity.start(this);
        finish();
    }

    @Override
    public void onLogInError(String error) {
        showForm();
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the user clicked on the "Log In" button.
     *
     * @param view The "Log In" button view.
     */
    public void onLogInClick(View view) {
        boolean hasErrors = false;

        TextView emailAddressTextView = (TextView)
                findViewById(R.id.activity_authenticator_email_address);
        TextView passwordTextView = (TextView) findViewById(R.id.activity_authenticator_password);
        String emailAddress = emailAddressTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        if (emailAddress.length() < 3) {
            hasErrors = true;
            emailAddressTextView.setError("The email address is not valid");
        }
        if (password.length() < 6) {
            hasErrors = true;
            passwordTextView.setError("The password must contain at least 6 characters");
        }

        if (hasErrors) return;

        authenticate(emailAddress, password);
    }

    /**
     * Try to authenticate the user by getting the account already created.
     * If no accounts were found, we show the form so that the user can log in.
     */
    private void autoAuthenticate() {
        mAccount = AccountHelper.getAccount(this);
        if (mAccount == null) {
            mHasAccount = false;
            showForm();
            return;
        }

        authenticate(mAccount.getEmailAddress(), mAccount.getPassword());
    }

    /**
     * Will call asynchronously the server to authenticate the user.
     * Will call onAuthenticationSuccess() or onAuthenticationError.
     *
     * @param emailAddress The user email address.
     * @param password     The user password.
     */
    private void authenticate(String emailAddress, String password) {
        hideForm();

        AuthenticationHelper ah = new AuthenticationHelper(this, emailAddress, password);
        ah.authenticate();
    }

    /**
     * Shows the form view and hides the progress bar.
     */
    private void showForm() {
        mFormView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Hides the form view and shows the progress bar.
     */
    private void hideForm() {
        mFormView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

}
