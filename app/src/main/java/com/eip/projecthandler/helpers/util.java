package com.eip.projecthandler.helpers;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.Format;
import java.util.Date;

public class util {

    /*** Date helper ****/

    public static String getDateString(Context context, Date date) {
        Format format = android.text.format.DateFormat.getDateFormat(context);
        return format.format(date.getTime());
    }

    public static JSONObject objectToJsonObject(Object obj) {
        JSONObject jsonObj = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(obj);
            jsonObj = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}
