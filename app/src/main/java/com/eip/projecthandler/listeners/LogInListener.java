package com.eip.projecthandler.listeners;

public interface LogInListener {

    void onLogInSuccess(String emailAddress, String password, String authToken);

    void onLogInError(String error);

}
