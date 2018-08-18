package com.alg.shopping.config;

/**
 * Created by Lenovo on 2017/6/12.
 */

import android.os.CountDownTimer;
import android.widget.TextView;

import com.alg.shopping.R;

import org.xutils.x;


/**
 * 验证码倒计时
 */
public class CountTimer extends CountDownTimer {
    TextView tv_timer;
    public CountTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setView(TextView tv_timer) {
        this.tv_timer = tv_timer;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tv_timer.setText("等待" + millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        tv_timer.setClickable(true);
        tv_timer.setText("获取验证码");
        tv_timer.setTextColor(x.app().getResources().getColor(R.color.colorwhite));
        tv_timer.setBackground(x.app().getResources().getDrawable(R.drawable.bg_order_btn_corners));
//        v_view.setBackgroundColor(x.app().getResources().getColor(R.color.colorThree));
    }
}