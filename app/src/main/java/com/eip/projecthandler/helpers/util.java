package com.eip.projecthandler.helpers;

import android.content.Context;

import java.text.Format;
import java.util.Date;

public class util {

    /*** Date helper ****/

    public static String getDateString(Context context, Date date) {
        Format format = android.text.format.DateFormat.getDateFormat(context);
        return format.format(date.getTime());
    }
}
