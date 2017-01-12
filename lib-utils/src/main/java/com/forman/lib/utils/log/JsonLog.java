package com.forman.lib.utils.log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonLog {

    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(Log.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(Log.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        Util.printLine(tag, true);
        message = headString + Log.LINE_SEPARATOR + message;
        String[] lines = message.split(Log.LINE_SEPARATOR);
        for (String line : lines) {
            android.util.Log.d(tag, "║ " + line);
        }
        Util.printLine(tag, false);
    }
}
