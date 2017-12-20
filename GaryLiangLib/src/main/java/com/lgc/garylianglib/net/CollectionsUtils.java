package com.lgc.garylianglib.net;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/10/17 15:16
 *     desc   :  map封装
 *     version: 1.0
 * </pre>
 */
public class CollectionsUtils {

    public static Map<Object, Object> generateMap(Object... str) {
        Map<Object, Object> map = null;
        if (null != str) {
            map = new HashMap<>();
            for (int i = 0; i < str.length; i++) {
                map.put(str[i], str[++i]);
            }
        }
        return map;
    }


}
