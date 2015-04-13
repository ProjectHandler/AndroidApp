package com.eip.projecthandler.services.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.eip.projecthandler.helpers.account.AccountAuthenticator;

public class AuthenticationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new AccountAuthenticator(this).getIBinder();
    }

}
