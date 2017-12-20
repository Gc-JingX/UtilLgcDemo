package com.lgc.garylianglib.util;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.view.View;

import com.lgc.garylianglib.R;
import com.lgc.garylianglib.util.data.ResUtil;
import com.lgc.garylianglib.util.data.SnackbarUtils;


/**
 * Created by Omesoft on 2016/11/18.
 */

public class CheckNetwork {

    /**
     * 单纯检查网络
     */
    public static boolean checkNetwork(final Context myContext) {
        // ProgressDialogUtil.show(myContext);
        boolean flag = false;
        ConnectivityManager CM = (ConnectivityManager) myContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (CM.getActiveNetworkInfo() != null)
            flag = CM.getActiveNetworkInfo().isAvailable();
        return flag;
    }
    public static boolean checkNetwork(final Context myContext,View view) {
        boolean flag = false;

        ConnectivityManager CM = (ConnectivityManager) myContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (CM.getActiveNetworkInfo() != null)
            flag = CM.getActiveNetworkInfo().isAvailable();
        if (flag){

            return flag;
        }else{
            showToast(view, ResUtil.getString(R.string.main_net_error));
        }

        return flag;
    }
    protected static void showToast(View view, String text) {

        SnackbarUtils.with(view)
                .setMessage(text)
                .setMessageColor(Color.WHITE)
                .show();
    }
}
