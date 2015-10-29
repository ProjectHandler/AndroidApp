package com.eip.projecthandler.constants;

import com.eip.projecthandler.models.SubTask;

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
    public static String USER_GET(long id) {return SERVER() + "/users/" + id; }

    // Projects
    public static final String PROJECT_GET_BY_USER = SERVER() + "/project/allByUser";

    //Tasks
    public static final String TASK_GET_BY_PROJECT(long projectId) {return SERVER() + "/task/allByProject/" + projectId;}
    public static final String TASK_GET_BY_PROJECT_AND_USER(long projectId) {return SERVER() + "/task/allByProjectAndUser/" + projectId;}
    public static final String TASK_UPDATE_SUBTASK_STATUS(SubTask subTask) {return SERVER() + "/task/updateSubTask/"+ subTask.getId() + "/" +
           subTask.isTaken() + "/" + subTask.isValidated();}

    // Tickets
    public static final String TICKET_GET_BY_PROJECT = SERVER() + "/ticket/allByProject";
    public static final String TICKET_GET_BY_USER = SERVER() + "/ticket/allByUser";
    public static final String TICKET_GET_BY_PROJECT_AND_USER(long projectId) {return SERVER() + "/ticket/allByProjectAndUser/" + projectId;}

}
