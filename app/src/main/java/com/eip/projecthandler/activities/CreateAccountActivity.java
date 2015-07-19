package com.eip.projecthandler.activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.AuthenticatorConstants;
import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.helpers.api.AuthenticationHelper;
import com.eip.projecthandler.listeners.LogInListener;
import com.eip.projecthandler.models.Account;

public class CreateAccountActivity extends AccountAuthenticatorActivity
        implements LogInListener {

    private View mFormView;
    private View mProgressBar;
    private EditText mEmailAddressTextView;
    private EditText mPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mFormView = findViewById(R.id.activity_authenticator_form);
        mProgressBar = findViewById(R.id.activity_authenticator_progress);
        mEmailAddressTextView = (EditText) findViewById(R.id.activity_authenticator_email_address);
        mPasswordTextView = (EditText) findViewById(R.id.activity_authenticator_password);

        if (!AccountHelper.hasAccount(this)) showForm();
        else {
            Toast.makeText(this, "already account, quit", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onAuthenticationSuccess(String emailAddress, String password, String authToken) {
        String accountType = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        String authTokenType = getIntent().getStringExtra(AuthenticatorConstants.AUTH_TOKEN_TYPE);

        if (accountType == null) accountType = getString(R.string.app_package_name);

        Bundle data = new Bundle();
        data.putString(AccountManager.KEY_ACCOUNT_NAME, emailAddress);
        data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        data.putString(AuthenticatorConstants.AUTH_TOKEN_TYPE, authTokenType);
        data.putString(AuthenticatorConstants.ACCOUNT_PASSWORD, password);

        Intent intent = new Intent();
        intent.putExtras(data);

        Account account = new Account(emailAddress, accountType);
        account.setPassword(password);
        AccountHelper.addAccount(this, account, authToken);
        setAccountAuthenticatorResult(data);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onAuthenticationError(String error) {
        showForm();
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the user clicked on the "Log In" button.
     * If credentials are valid, the user is authenticated.
     *
     * @param view The "Log In" button view.
     */
    public void onLogInClick(View view) {
        String emailAddress = mEmailAddressTextView.getText().toString();
        String password = mPasswordTextView.getText().toString();

        if (checkCredentials(emailAddress, password))
            authenticate(emailAddress, password);
    }

    /**
     * Checks if credentials are valid.
     *
     * @param emailAddress The email address.
     * @param password     The password.
     * @return True if credentials are valid, false otherwise.
     */
    private boolean checkCredentials(String emailAddress, String password) {
        boolean hasErrors = false;

        if (emailAddress.length() < 3) {
            hasErrors = true;
            mEmailAddressTextView.setError("The email address is not valid");
        }
        if (password.length() < 6) {
            hasErrors = true;
            mPasswordTextView.setError("The password must contain at least 6 characters");
        }

        return !hasErrors;
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
        AuthenticationHelper.authenticate(this, this, emailAddress, password);
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
