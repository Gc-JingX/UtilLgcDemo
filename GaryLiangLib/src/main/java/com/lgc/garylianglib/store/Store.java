package com.lgc.garylianglib.store;




import com.lgc.garylianglib.actions.Action;
import com.lgc.garylianglib.dispater.Dispatcher;

import java.util.Iterator;
import java.util.Set;

/**
 * 数据状态
 * <p/>
 * Created by wangchenlong on 15/8/17.
 */
public abstract class Store {
    private final Dispatcher mDispatcher; // 调度器

    protected Store(Dispatcher dispatcher) {
        mDispatcher = dispatcher;
    }

    // 通知改变
    public void emitStoreChange() {
        mDispatcher.emitChange(changeEvent());
    }

    public void emitStoreChange(String event) {

        mDispatcher.emitChange(event);
    }

    // 改变事件, 由子类重写
    public abstract StoreChangeEvent changeEvent();

    @SuppressWarnings("unused")
    public abstract void onAction(Action action);


    public String getMsg(Action action) {
        Object obj = action.getData().get(Action.KEY_MSG);
        return obj == null ? "" : (String) obj;
    }

    public void clearData() {
    }

    public static abstract class StoreChangeEvent {
        // 以下code用于list加载数据使用,
        // 同一个StoreChangeEvent接收事件时,同时只能作为一个list的更新数据code,
        // 多个list多处使用时请重新定义
        public static final int CODE_ACTION_LIST_REFRESH_SUCCESS = 0x991;
        public static final int CODE_ACTION_LIST_REFRESH_FAIL = 0x992;
        public static final int CODE_ACTION_LIST_LOAD_MORE_SUCCESS = 0x993;
        public static final int CODE_ACTION_LIST_LOAD_MORE_FAIL = 0x994;
        public static final int CODE_ACTION_LIST_NOT_HAVE_DATA = 0x995;
        public static final int CODE_ACTION_LIST_NOT_HAVE_MORE_DATA = 0x996;

        public int code;
        public int resultCode;
        public String msg = "";
    }

    /**
     * 传递参数
     *
     * @param action
     */
    public void loadText(Action action) {
        Set set = action.getData().keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();


            emitStoreChange(key);
        }
    }
}
