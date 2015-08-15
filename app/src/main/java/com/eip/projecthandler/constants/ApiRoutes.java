package com.eip.projecthandler.constants;

public class ApiRoutes {

    // Debug mode
    private static final boolean DEBUG = true;

    // Servers
    private static final String SERVER_PROD = "http://163.5.84.233:8080/projecthandler/api";
    private static final String SERVER_DEV = "http://192.168.1.8:8080/projecthandler/api";
    //private static final String SERVER_DEV = "http://192.168.1.94:8080/projecthandler/api";

    public static String SERVER() {
        return DEBUG ? SERVER_DEV : SERVER_PROD;
    }

    // Authentication
    public static String authentication(String emailAddress, String password) { return SERVER() + "/user/authenticate?email=" + emailAddress + "&password=" + password; }

    // Users
    public static final String USERS_GET = SERVER() + "/users";
    public static String USER_GET(long id) {
        return SERVER() + "/users/" + id;
    }

    // Projects
    public static final String PROJECT_GET_BY_USER = SERVER() + "/project/allByUser";

    // Tickets
    //public static final String TICKET_GET_ALL = SERVER() + "/ticket/all";
    public static final String TICKET_GET_BY_USER = SERVER() + "/ticket/allByUser";

}
