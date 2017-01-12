package com.forman.lib.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    //当天
    public static final String FORMAT_CURDAY = "hh:mm";
    private final static int SECONDS = 1;
    private final static int MINUTES = 60 * SECONDS;
    private final static int HOURS = 60 * MINUTES;
    private final static int DAYS = 24 * HOURS;
    private final static int Yesterday = 48 * HOURS;
    private final static int BeforeYesterDay = 72 * HOURS;
    private final static int WEEKS = 6 * DAYS;
    private final static int MONTHS = 4 * WEEKS;
    private final static int YEARS = 12 * MONTHS;
    private final static String WEEK = "星期%s";
    private final static String YESTERDAY = "昨天";
    private final static String BEFOREYESTERDAY = "前天";

    public static String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static Long getTimeTurnMs(String time) {
        if (TextUtils.isEmpty(time))
            return 0L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            return format.parse(time.toString()).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String getNormalTime(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(value));
        return time;
    }

    /**
     * String转日期
     */
    public static Date stringToDate(String format, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 日期转String
     */
    public static String dateToString(String format, Date date) {
        return android.text.format.DateFormat.format(format, date).toString();
    }

    public static String stringToFormat(String format, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return android.text.format.DateFormat.format(format, sdf.parse(time)).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 统一距离现在多久
     *
     * @param date
     * @return
     */
    public static String getTimeAgo(Date date) {
        int beforeSeconds = (int) (date.getTime() / 1000);
        int nowSeconds = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
        int timeDifference = nowSeconds - beforeSeconds;
        if (timeDifference < MINUTES) {//一分钟内
            return timeDifference + "s";
        } else if (timeDifference < HOURS) {//一小时内
            return timeDifference / 60 + "分钟";
        } else if (timeDifference < DAYS) {//一天内
            return timeDifference / 60 / 60 + "小时";
        } else if (timeDifference < MONTHS) {
            return timeDifference / 60 / 60 / 24 + "天";
        } else if (timeDifference < YEARS) {
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(date);
            Calendar toDayCalendar = Calendar.getInstance();
            toDayCalendar.setTimeInMillis(System.currentTimeMillis());
            return getMonthsOfAge(fromCalendar, toDayCalendar) + "月";
        } else {
            return timeDifference / 60 / 60 / 24 / 365 + "年";
        }
    }

    public static int getMonthsOfAge(Calendar calendarBirth, Calendar calendarNow) {
        return (calendarNow.get(Calendar.YEAR) - calendarBirth
                .get(Calendar.YEAR)) * 12 + calendarNow.get(Calendar.MONTH)
                - calendarBirth.get(Calendar.MONTH);
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static String formatTime(Long ms) {
        long time = new Date().getTime() - ms;

        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = time / dd;
        Long hour = (time - day * dd) / hh;
        Long minute = (time - day * dd - hour * hh) / mi;
        Long second = (time - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = time - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();

        if (day > 0 || hour > 12) {
            return formatDate(ms);
        } else if (hour > 1) {
            return sb.append(hour).append("小时前").toString();
        } else {
            if (minute <= 0) {
                return sb.append("刚刚").toString();
            } else {
                return sb.append(minute).append("分钟前").toString();
            }
        }
    }

    public static String formatDate(long ms) {
        String time = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date(ms));
        return time;
    }
}
