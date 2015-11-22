package com.eip.projecthandler.constants;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eip.projecthandler.helpers.DBOpenHelper;
import com.eip.projecthandler.models.SubTask;

public class ApiRoutes {

    // Debug mode
    private static final boolean DEBUG = true;

    private static final String PH_API_URL = "/projecthandler/api";

    // Servers
    private static final String SERVER_PROD = "http://163.5.84.233:8080" + PH_API_URL;
    private static final String SERVER_DEV ="http://163.5.84.233:8080" + PH_API_URL;
    //private static final String SERVER_DEV = "http://192.168.1.143:8080/projecthandler/api";
    //private static final String SERVER_DEV = "http://192.168.1.94:8080/projecthandler/api";

    public static String SERVER() {
        return DEBUG ? SERVER_DEV : SERVER_PROD;
    }

    public static String SERVER(Activity activity) {
        //return DEBUG ? SERVER_DEV : SERVER_PROD;
        DBOpenHelper tdb;
        SQLiteDatabase sdb;
        tdb = new DBOpenHelper(activity, "server.db", null, 1);
        sdb = tdb.getReadableDatabase();

        String address;

        String[] columns = {"ADDRESS"};
        Cursor c = sdb.query("server", columns, null, null, null, null, null);
        c.moveToFirst();

        if (c.getCount() == 1)
            address = c.getString(0);
        else
            address = null;

        return address + PH_API_URL;
    }

    // Authentication
    public static String authentication(String emailAddress, String password, Activity activity) {
        return SERVER(activity) + "/user/authenticate?email=" + emailAddress + "&password=" + password;
    }

    // Users
    public static final String USERS_GET(Activity activity) {
        return SERVER(activity) + "/users";
    }
    public static String USER_GET(Activity activity, long id) {
        return SERVER(activity) + "/users/" + id;
    }

    // Projects
    public static final String PROJECT_GET_BY_USER(Activity activity) {
        return SERVER(activity) + "/project/allByUser";
    }

    //Tasks
    public static final String TASK_GET_BY_PROJECT(Activity activity, long projectId) {
        return SERVER(activity) + "/task/allByProject/" + projectId;
    }
    public static final String TASK_GET_BY_PROJECT_AND_USER(Activity activity, long projectId) {
        return SERVER(activity) + "/task/allByProjectAndUser/" + projectId;
    }
    public static final String TASK_UPDATE_SUBTASK_STATUS(Activity activity, SubTask subTask) {
        return SERVER(activity) + "/task/updateSubTask/"+ subTask.getId() + "/" + subTask.isTaken() + "/" + subTask.isValidated();
    }

    // Tickets
    public static final String TICKET_GET_BY_PROJECT(Activity activity, long projectId) {
        return SERVER(activity) + "/ticket/allByProject/"+projectId;
    }
    public static final String TICKET_GET_BY_USER(Activity activity) {
        return SERVER(activity) + "/ticket/allByCurrentUser";
    }
    public static final String TICKET_GET_BY_PROJECT_AND_USER(Activity activity, long projectId) {
        return SERVER(activity) + "/ticket/allByProjectAndUser/" + projectId;}
    public static final String TICKET_SAVE_NEW_TICKET_MESSAGE(Activity activity, Long ticketId) {
        return SERVER(activity) + "/ticket/saveNewTicketMessage/" + ticketId;
    }
    public static final String TICKET_GET_DATA_FOR_NEW_TICKET(Activity activity) {
        return SERVER(activity) + "/ticket/dataForNewTicket";
    }
    public static final String TICKET_SAVE_NEW_TICKET(Activity activity) {
        return SERVER(activity) + "/ticket/saveNewTicket";
    }
}
