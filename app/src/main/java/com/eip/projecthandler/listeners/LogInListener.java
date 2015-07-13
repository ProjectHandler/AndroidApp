package com.eip.projecthandler.listeners;

public interface LogInListener {

    void onAuthenticationSuccess(String emailAddress, String password, String authToken);

    void onAuthenticationError(String error);
}
