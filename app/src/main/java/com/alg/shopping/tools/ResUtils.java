package com.alg.shopping.tools;

import org.xutils.x;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ResUtils {
    /**
     * 获取String
     * @param strId
     */
    public static String getStringRes(int strId) {
        String str = x.app().getString(strId);
        return str;
    }
}
