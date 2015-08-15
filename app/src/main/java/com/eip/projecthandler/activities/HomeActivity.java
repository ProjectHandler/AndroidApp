package com.eip.projecthandler.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.activities.NavigationDrawerActivity;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsMenuHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerBottomHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerTopHandler;
import com.blunderer.materialdesignlibrary.models.Account;
import com.eip.projecthandler.R;
import com.eip.projecthandler.fragments.HomeFragment;
import com.eip.projecthandler.fragments.ListProjectFragment;
import com.eip.projecthandler.fragments.ListTicketFragment;
import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.helpers.api.AuthenticationHelper;
import com.eip.projecthandler.listeners.LogOutListener;

public class HomeActivity extends NavigationDrawerActivity implements LogOutListener {

    /**
     * Starts HomeActivity.
     *
     * @param context The context of the Activity that calls this method.
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AccountHelper.hasAccount(this)) {
            LogInActivity.start(this);
            finish();
        }
    }

    @Override
    public void onLogOutSuccess() {
        LogInActivity.start(this);
        finish();
    }

    @Override
    public void onLogOutError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public NavigationDrawerAccountsHandler getNavigationDrawerAccountsHandler() {
        return null;
    }

    @Override
    public NavigationDrawerAccountsMenuHandler getNavigationDrawerAccountsMenuHandler() {
        return null;
    }

    @Override
    public void onNavigationDrawerAccountChange(Account account) {
    }

    @Override
    public NavigationDrawerTopHandler getNavigationDrawerTopHandler() {
        return new NavigationDrawerTopHandler(this)
                .addItem(R.string.home, R.drawable.ic_add, new HomeFragment())
                .addItem(R.string.home, R.drawable.ic_help, new HomeFragment())
                .addItem(R.string.projects, R.drawable.ic_add, new ListProjectFragment())
                .addItem(R.string.tickets, R.drawable.ic_add, new ListTicketFragment());
    }

    @Override
    public NavigationDrawerBottomHandler getNavigationDrawerBottomHandler() {
        return new NavigationDrawerBottomHandler(this)
                .addItem(R.string.log_out, R.drawable.ic_action_logout, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AuthenticationHelper.logOut(HomeActivity.this, HomeActivity.this);
                    }

                });
    }

    @Override
    public boolean overlayActionBar() {
        return false;
    }

    @Override
    public boolean replaceActionBarTitleByNavigationDrawerItemTitle() {
        return true;
    }

    @Override
    public int defaultNavigationDrawerItemSelectedPosition() {
        return 0;
    }

}
