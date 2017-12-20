package com.lgc.garylianglib.util.data;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }


     static String leftTime = null;
    public static void setLeftTime(long endtime, final TextView textView) {
        if (endtime <= 0) return ;
        Date date = convertTimeToDate(endtime+ "");
        long timeDiff = (date.getTime() - getExactlyCurrentTime());
        if (timeDiff>0){
            CountDownTimer mCountDownTimer = new CountDownTimer(timeDiff, 1000) {
                @Override
                public void onTick(long l) {
                    leftTime= formatTimeLeft(l);
                    textView.setText("剩余 "+leftTime);
                }

                @Override
                public void onFinish() {
                    leftTime="活动已结束";
                }
            };
            mCountDownTimer.start();
        }

    }

    //单位秒
    public static String formatTimeLeft(long ms) {
        long day = ms / (1000 * 60 * 60 * 24);
        long hour = (ms % (1000 * 60 * 60 * 24) + day * 24 * 60 * 60 * 1000) / (1000 * 60 * 60);
        long min = (ms % (1000 * 60 * 60)) / (1000 * 60);
        long sec = (ms % (1000 * 60)) / 1000;
        if (day > 0) {
            return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
        }
        if (hour > 0) {
            return hour + "小时" + min + "分钟" + sec + "秒";
        }
        if (min > 0) {
            return min + "分钟" + sec + "秒";
        }
        return sec + "秒";
    }

    public static String convertTimeLeft(long ms) {
        long day = ms / (1000 * 60 * 60 * 24);
        long hours = (ms % (1000 * 60 * 60 * 24) + day * 24 * 60 * 60 * 1000) / (1000 * 60 * 60);
        long minutes = (ms % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (ms % (1000 * 60)) / 1000;
        return hours + "时" + minutes + "分"
                + seconds+"秒";
    }

    public static long convertTimeLeft(String formatStr){
        if (formatStr==null){
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d ;
        long time = 0;
        try {
            d = format.parse(formatStr);
            if (d!=null){
                time = d.getTime();//单位毫秒
            }else{
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time/1000;
    }


    public static String[] formatTimer(long sec) {
        String[] s = new String[4];
        long days = sec / (60 * 60 * 24);
        long hours = (sec % (60 * 60 * 24)) / (60 * 60);
        long minutes = (sec % (60 * 60)) / (60);
        long seconds = (sec % (60));
        if (days<10){
            s[0] = "0"+days;
        }else{
            if (days>99){
                s[0] = 99+"";
            }else{
                s[0] = days+"";
            }
        }
        if (hours<10){
            s[1] = "0"+hours;
        }else{
            s[1] = hours+"";
        }
        if (minutes<10){
            s[2] = "0"+minutes;
        }else{
            s[2] = minutes+"";
        }
        if (seconds<10){
            s[3] = "0"+seconds;
        }else{
            s[3] = seconds+"";
        }
        return s;
//        return seconds+"秒/"+minutes + "分钟/"+ hours + "小时/"+days + "天";
    }

    public static Date convertTimeToDate(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp));
        return date;
    }

    public static long delta = -1;
    public static long getExactlyCurrentTime() {
        return System.currentTimeMillis() - delta;
    }

    //获取剩余时间,由于用的是之前的时间，现在倒过来
    public static long getCurrentLeftTime(long endTime) {
        Date date = TimeUtils.convertTimeToDate(endTime+"");
//        return date.getTime()-System.currentTimeMillis() - delta;
        return System.currentTimeMillis() - delta-date.getTime();
    }

}
