package com.forman.lib.utils.log;


public class BaseLog {

    public static void printDefault(int type, String tag, String msg) {

        int index = 0;
        int maxLength = 4000;
        int countOfSub = msg.length() / maxLength;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                printSub(type, tag, sub);
                index += maxLength;
            }
            printSub(type, tag, msg.substring(index, msg.length()));
        } else {
            printSub(type, tag, msg);
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case Log.V:
                android.util.Log.v(tag, sub);
                break;
            case Log.D:
                android.util.Log.d(tag, sub);
                break;
            case Log.I:
                android.util.Log.i(tag, sub);
                break;
            case Log.W:
                android.util.Log.w(tag, sub);
                break;
            case Log.E:
                android.util.Log.e(tag, sub);
                break;
            case Log.A:
                android.util.Log.wtf(tag, sub);
                break;
        }
    }

}
