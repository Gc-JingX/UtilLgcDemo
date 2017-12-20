package com.lgc.garylianglib.actions;


import com.lgc.garylianglib.R;
import com.lgc.garylianglib.dispater.Dispatcher;
import com.lgc.garylianglib.util.data.ResUtil;

/**
 * Created by omesoft_lgc on 2017/9/8.
 */

public class ActionCreator {
    public final static String NETWORK_TIPS;

    static {
        NETWORK_TIPS = ResUtil.getString(R.string.main_service_error);
    }

    public static int  netError=-1;

    private static ActionCreator actionCreator;
    private static Dispatcher dispatcher;


    public static ActionCreator getInstance(Dispatcher _dispatcher) {
        if (actionCreator == null) {
            actionCreator = new ActionCreator(_dispatcher);
        }
        dispatcher = _dispatcher;
        return actionCreator;
    }

    public ActionCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }


    /**
     * 发送事件
     *
     * @param type
     * @param data
     */
    public void sendEvent(String type, int errorType, Object... data) {
        dispatcher.dispatch(type, errorType, data);
    }


    public void updateChange() {
//        dispatcher.dispatch(LoginAction.STUDENT_INPUT_CHANGE, LoginAction.STUDENT_INPUT_CHANGE);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

}
