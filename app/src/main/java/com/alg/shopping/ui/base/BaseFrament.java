package com.alg.shopping.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.alg.shopping.R;
import com.alg.shopping.bean.EventBean;
import com.alg.shopping.tools.NormalUtils;
import com.alg.shopping.tools.StatusBarHelper;
import com.alg.shopping.ui.consult.ConsultFrament;
import com.alg.shopping.ui.home.HomeFrament;
import com.alg.shopping.ui.mine.MineFrament;
import com.alg.shopping.ui.trade.TradeFrament;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by QiuQiu on 2017/11/19.
 */

public abstract class BaseFrament extends Fragment {
    protected ViewGroup view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setLayouts(inflater,container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Subscribe
    public void onEventMainThread(EventBean event) {
        reciverMesssage(event);
    }
    protected abstract void reciverMesssage(EventBean obj);
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this instanceof TradeFrament || this instanceof MineFrament || this instanceof ConsultFrament || this instanceof HomeFrament){
            addStatusBar();
        }
        initViews();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    protected abstract void setLayouts(LayoutInflater inflater, @Nullable ViewGroup container);
    protected  abstract void initViews();
    protected View mStatusBarView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addStatusBar() {
        mStatusBarView = new View(getContext());
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int statusBarHeight = NormalUtils.getStatusBarHeight(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, statusBarHeight);
        mStatusBarView.setLayoutParams(params);
        mStatusBarView.requestLayout();
        mStatusBarView.setBackgroundColor(R.color.shallowcenter_color);
        if (view != null){
            view.addView(mStatusBarView, 0);
        }
    }
    protected Unbinder unbinder;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
