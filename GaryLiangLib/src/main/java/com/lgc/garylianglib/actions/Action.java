package com.lgc.garylianglib.actions;

import android.util.Log;

import java.util.HashMap;

/**
 * Action(固定样式)事件, 主要由类型和数据, 两部分组成
 * 通过其中的静态类Builder逐个添加Key-Value进行构建.
 *
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/10 8:36
 */
public class Action {
    public final static String ACTION_TOKEN_EXPIRE = "action_token_expired";
    public static final String ACTION_FAILURE_BY_RESPONSE_CODE = "ACTION_FAILURE_BY_RESPONSE_CODE";
    public static final String ACTION_FAILURE = "ACTION_FAILURE";
    public static final String ACTION_SUCCESS = "ACTION_SUCCESS";

    public static final String KEY_PAGE = "KEY_PAGE";
    public static final String KEY_MSG = "KEY_MSG";

    public static final String KEY_OBJ_1 = "KEY_OBJ_1";
    public static final String KEY_OBJ_2 = "KEY_OBJ_2";

    public static final String TYPE_FINISH = "TYPE_FINISH";

    public static final String KEY_OBJ = "key_default_action_obj_";


    private final String type;
    private final int errorType; //错误 类型

    private final HashMap<String, Object> data;

    public static String getKey(int which) {
        return KEY_OBJ + which;
    }

    Action(String type, int errorType, HashMap<String, Object> data) {
        this.type = type;
        this.data = data;
        this.errorType = errorType;
    }

    public static Builder type(String type, int errorType) {
        return new Builder().with(type, errorType);
    }

    public String getType() {
        return type;
    }

    public int getErrorType() {
        return errorType;
    }
    public HashMap getData() {
        return data;
    }

    //镜头Builder
    public static class Builder {
        private String mType; // 类型
        private int mError; //错误 类型
        private HashMap<String, Object> mData; // 数据

        // 通过类型创建Builder
        public Builder with(String type, int errorType) {
            if (type == null) {
                throw new IllegalArgumentException("Type may not be null.");
            }
            mType = type;
            mError = errorType;
            mData = new HashMap<>();
            return this;
        }

        // 绑定数据
        public Builder bundle(String key, Object value) {
            if (key == null || value == null) {
                throw new IllegalArgumentException("Key or value may not be null.");
            }
            mData.put(key, value);
            return this;
        }

        // 通过Builder创建Action
        public Action build() {
            if (mType == null || mType.isEmpty()) {
                throw new IllegalArgumentException("At least one key is required.");
            }

            Log.e("xx", " mType " + mType + " mData " + mData.toString());
            return new Action(mType, mError, mData);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "mType='" + mType + '\'' +
                    ", mError=" + mError +
                    ", mData=" + mData +
                    '}';
        }
    }
    public  String getMsg(Action action) {
        Object obj = action.getData().get(Action.KEY_MSG);
        return obj == null ? "" : (String) obj;
    }
    public Object getUserData() {
        return data.get(Action.KEY_OBJ);
    }
    @Override
    public String toString() {
        return "Action{" +
                "type='" + type + '\'' +
                ", errorType=" + errorType +
                ", data=" + data +
                '}';
    }
}
