package com.alg.shopping.config;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;


import com.alg.shopping.R;

import org.xutils.x;

/**
 * Created by Lenovo on 2017/7/10.
 */

public class CusClickableSpan extends ClickableSpan {
    String string;
    Context context;
    spanOnClick mSpanOnClick;
    int color;
    float textSize = x.app().getResources().getDimension(R.dimen.text_midd_size);

    /**
     * @param str          需点击的文字
     * @param context      上下文
     * @param mSpanOnClick 点击回调
     */
    public CusClickableSpan(String str, Context context, spanOnClick mSpanOnClick, int color, float textSize) {
        super();
        this.string = str;
        this.context = context;
        this.mSpanOnClick = mSpanOnClick;
        this.color = color;
        this.textSize = textSize;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setTextSize(textSize);
        ds.setColor(color);
    }

    @Override
    public void onClick(View widget) {
        ((TextView)widget).setHighlightColor(context.getResources().getColor(android.R.color.transparent));
        if (mSpanOnClick != null) {
            mSpanOnClick.spanOnClick();
        }
    }

    public interface spanOnClick {
        void spanOnClick();
    }
}
