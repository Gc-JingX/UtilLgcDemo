package com.lgc.demo.util.net;


import com.lgc.demo.BuildConfig;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/11/9 9:54
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class URLManager {

    static {
        //配合retrofit，需要以/结尾
        if (BuildConfig.DEBUG) {
            BASE_URL = "http://tea.51feijin.com/api/";
        } else {
            BASE_URL = "http://tea.51feijin.com/api/";
        }
    }

    public static final String BASE_URL;
    /**
     * 关于我们
     */
    public static final String GET_ABOUT = "my/about";
    /**
     * 联系我们
     */
    public static final String GET_CONTACT = "my/contact";
}



