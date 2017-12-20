package com.lgc.garylianglib.util.data;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/11/16 19:15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FormatUtils {

    public static String format(int resId, Object... text) {
        return format(ResUtil.getString(resId), text);
    }

    public static String format(String src, Object... text) {
        return String.format(src, text);
    }

}
