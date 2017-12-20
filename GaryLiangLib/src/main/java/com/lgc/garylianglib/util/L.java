package com.lgc.garylianglib.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 12:01
 */
public class L {

    public static boolean isDebug = true;
    private static final String TAG = "lgc";
    /**
     * logcat在实现上对于message的内存分配大概,2k左右, 所以超过的内容都直接被丢弃,设置文本长度超过LOG_MAXLENGTH分多条打印
     */
    private final static int LOG_MAXLENGTH = 2048;

    // 记录上次的logLocation
    private static String lastLogMethod = "";
    private static String jumpKeyWord = "   ---------☞ ";


    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, logContent(msg) + logLocation(0));
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, logContent(msg) + logLocation(0));
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, logContent(msg) + logLocation(0));
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, logContent(msg) + logLocation(0));
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(TAG, logContent(msg) + logLocation(0));
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, logContent(msg) + logLocation(0));
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, logContent(msg) + logLocation(0));
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, logContent(msg) + logLocation(0));
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, logContent(msg) + logLocation(0));
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, logContent(msg) + logLocation(0));
    }

    public static void sys(String tag, String msg) {
        if (isDebug)
            System.out.println(tag + " : " + logContent(msg));
    }

    public static void sys(String msg) {
        if (isDebug)
            System.out.println(logContent(msg));
    }
    /**
     * 打印异常
     *
     * @param msg
     * @param e
     */
    public static void e(String msg, Exception e) {
        if (isDebug) {
            Log.e(TAG, msg + logLocation(0), e);
        }
    }
    /**
     * 打印json格式文本
     *
     * @param json
     */
    public static void json(String json) {
        if (isDebug) {
            json("", json);
        }
    }

    /**
     * 打印json格式文本
     *
     * @param prefix 前缀文本
     * @param json
     */
    public static void json(String prefix, String json) {
        if (isDebug) {
            String text = prefix + fomatJson(json);
            Log.i(TAG, logContent(text) + logLocation(0));
        }
    }

    /**
     * json格式化
     *
     * @param jsonStr
     * @return
     */
    private static String fomatJson(String jsonStr) {
        try {
            jsonStr = jsonStr.trim();
            if (jsonStr.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.toString(2);
            }
            if (jsonStr.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonStr);
                return jsonArray.toString(2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Json格式有误: " + jsonStr;
    }

    /**
     * 打印内容
     *
     * @param text
     * @return
     */
    private static String logContent(String text) {
        if (text.length() < 50) {// 内容长度不超过50，前面加空格加到50
            int minLeng = 50 - text.length();
            // Log.i(logtag, "leng========" + leng + "   " + text.length());
            if (minLeng > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < minLeng; i++) {
                    stringBuilder.append(" ");
                }
                text = text + stringBuilder.toString();
            }
        } else if (text.length() > LOG_MAXLENGTH) {// 内容超过logcat单条打印上限，分批打印
            //Log.i(logtag, "text长度=========" + text.length());
            int logTime = text.length() / LOG_MAXLENGTH;
            for (int i = 0; i < logTime; i++) {
                String leng = text.substring(i * LOG_MAXLENGTH, (i + 1)
                        * LOG_MAXLENGTH);
                // 提示
                if (i == 0) {
                    Log.i(TAG, "打印分" + logTime + "条显示 :" + leng);
                } else {
                    Log.i(TAG, "接上条↑" + leng);
                }

            }
            text = "接上条↑"
                    + text.substring(logTime * LOG_MAXLENGTH, text.length());
        }
        return text;
    }

    /**
     * 定位打印的方法
     *
     * @return
     */
    private static StringBuilder logLocation(int index) {
        StackTraceElement logStackTrace = getLogStackTrace(index);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(jumpKeyWord).append(" (").append(logStackTrace.getFileName())
                .append(":").append(logStackTrace.getLineNumber() + ")");
        // Log.i(logtag, "leng========" + stringBuilder + "   " +
        // lastLogMethod);
        if (stringBuilder.toString().equals(lastLogMethod)) {
            stringBuilder = new StringBuilder("");
        } else {
            lastLogMethod = stringBuilder.toString();
        }

        return stringBuilder;
    }

    /**
     * 获取调用打印方法的栈 index 调用打印i/e/json的index为0
     *
     * @return
     */
    private static StackTraceElement getLogStackTrace(int index) {
        StackTraceElement logTackTraces = null;
        StackTraceElement[] stackTraces = Thread.currentThread()
                .getStackTrace();
        // Log.i(logtag, JSONSerializer.toJson(stackTraces));
        for (int i = 0; i < stackTraces.length; i++) {
            StackTraceElement stackTrace = stackTraces[i];
            if (stackTrace.getClassName().equals(L.class.getName())) {
                // getLogStackTrace--logLocation--i/e/json--方法栈,所以调用打印方法栈的位置是当前方法栈后的第3个
                logTackTraces = stackTraces[i + 3 + index];
                i = stackTraces.length;
            }
        }
        return logTackTraces;
    }

}
