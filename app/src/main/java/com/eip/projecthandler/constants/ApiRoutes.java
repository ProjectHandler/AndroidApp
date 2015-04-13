package com.eip.projecthandler.constants;

public class ApiRoutes {

    // Debug mode
    private static final boolean DEBUG = true;

    // Servers
    public static final String SERVER_PROD = "http://projecthandler.fr";
    public static final String SERVER_DEV = "http://dev.projecthandler.fr";

    public static String SERVER() {
        return DEBUG ? SERVER_DEV : SERVER_PROD;
    }

    // Users
    public static final String USERS_GET = SERVER() + "/users";

    public static String USER_GET(long id) {
        return SERVER() + "/users/" + id;
    }

}
