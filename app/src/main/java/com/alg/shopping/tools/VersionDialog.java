package com.alg.shopping.tools;

import android.app.Dialog;
import android.content.Context;

public class VersionDialog extends Dialog {
    private int res;

    public VersionDialog(Context context, int theme, int res) {
        super(context, theme);
        // TODO 自动生成的构造函数存根
        setContentView(res);
        this.res = res;
        setCanceledOnTouchOutside(false);
    }

}
