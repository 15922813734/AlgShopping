package com.alg.shopping.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.alg.shopping.inter.ScrollViewListener;


/**
 * Created by XB on 2018/3/20.
 */

public class MScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public MScrollView(Context context) {
        super(context);
    }

    public MScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public MScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }


}
