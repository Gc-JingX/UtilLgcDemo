package com.lgc.garylianglib.actions;


import android.util.Log;

import com.lgc.garylianglib.R;
import com.lgc.garylianglib.event.EventBusUtils;
import com.lgc.garylianglib.util.data.ResUtil;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/12/22 15:30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ActionCreator {
    public final static String NETWORK_TIPS;

    static {
        NETWORK_TIPS = ResUtil.getString(R.string.main_service_error);
    }

    public static int netError = -1;

    private void post(final Object event) {
        try {
            EventBusUtils.post(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEvent(String type, int errorType, Object... data) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("StoreData must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type, errorType);
//        actionBuilder.clazz(actionClazz);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        Log.e("xx", "准备post------->");
        post(actionBuilder.build());
    }

    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }
}
