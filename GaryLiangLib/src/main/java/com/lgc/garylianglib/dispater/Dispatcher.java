package com.lgc.garylianglib.dispater;

import android.util.Log;

import com.lgc.garylianglib.actions.Action;
import com.lgc.garylianglib.store.Store;
import com.squareup.otto.Bus;


/**
 * 调度器
 *
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/10 8:37
 */
public class Dispatcher {

    private final Bus mBus;
    private static Dispatcher sInstance;

    private Dispatcher(Bus bus) {
        mBus = bus;
    }

    public static Dispatcher getInstance(Bus bus) {
        if (sInstance == null) {
            sInstance = new Dispatcher(bus);
        }
        return sInstance;
    }

    public void register(final Object cls) {
        mBus.register(cls);
    }

    public void unregister(final Object cls) {
        mBus.unregister(cls);
    }

    private void post(final Object event) {
        try {
            if (mBus != null) {

                mBus.post(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 每个状态改变都需要发送事件, 由View相应, 做出更改
    public void emitChange(Store.StoreChangeEvent o) {
        post(o);
    }

    public void emitChange(String o) {
        post(o);
    }


    public void dispatch(String type, int errorType, String key) {
        if (type == null || type.isEmpty()) { // 数据空
            throw new IllegalArgumentException("Type must not be empty");
        }
        Action.Builder actionBuilder = Action.type(type,errorType);
        Log.e("xx", "type " + type + " key " + key + " value ");
        actionBuilder.bundle(key, ""); // 放置键值
        // 发送到EventBus
        post(actionBuilder.build());
    }

    //Class<?> actionClazz,
    public void dispatch(String type, int errorType, Object... data) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("StoreData must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type,errorType);
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
