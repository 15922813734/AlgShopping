package com.alg.shopping.inter;


import com.alg.shopping.ui.view.MScrollView;

/**
 * Created by XB on 2018/3/20.
 */

public interface ScrollViewListener {
    void onScrollChanged(MScrollView scrollView, int x, int y, int oldx, int oldy);
}
