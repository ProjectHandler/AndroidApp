package com.eip.projecthandler.models;

public class Account extends android.accounts.Account {

    private String mPassword;

    public Account(String emailAddress, String type) {
        super(emailAddress, type);
    }

    /**
     * @return The account email address.
     */
    public String getEmailAddress() {
        return super.name;
    }

    /**
     * @return The account password.
     */
    public String getPassword() {
        return mPassword;
    }

    /**
     * Sets the account password.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        mPassword = password;
    }

    /**
     * @return The account type.
     */
    public String getType() {
        return super.type;
    }

}
