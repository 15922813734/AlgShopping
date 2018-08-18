package com.alg.shopping.ui.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.alg.shopping.R;
import com.alg.shopping.bean.EventBean;
import com.alg.shopping.contants.ActionFlag;
import com.alg.shopping.contants.Constants;
import com.alg.shopping.tools.CacheUtils;
import com.alg.shopping.tools.NormalUtils;
import com.alg.shopping.ui.base.BaseActivity;
import com.alg.shopping.ui.consult.ConsultFrament;
import com.alg.shopping.ui.home.HomeFrament;
import com.alg.shopping.ui.mine.MineFrament;
import com.alg.shopping.ui.trade.TradeFrament;
import com.yzq.zxinglibrary.common.Constant;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.f_frament)
    FrameLayout f_frament;
    FragmentManager fm;
    Fragment f_home,f_consult,f_trade,f_mine,currFrament;
    List<Fragment> mFragments = new ArrayList<>();
    int index = -1;//1-跳转到订单列表页

    @BindView(R.id.rb_home)
    RadioButton rb_home;
    @BindView(R.id.rb_consult)
    public RadioButton rb_consult;
    @BindView(R.id.rb_trade)
    RadioButton rb_trade;
    @BindView(R.id.rb_mine)
    RadioButton rb_mine;

    boolean isInCreate = false;
    RadioButton radioButtonId = rb_home;//当前展示的按钮
    int currClikcButtonId = -1;//当前点击的按钮
    @Override
    protected void reciverMesssage(EventBean obj) {

    }

    private void setCheck(RadioButton rb){
        rb_home.setChecked(false);
        rb_consult.setChecked(false);
        rb_trade.setChecked(false);
        rb_mine.setChecked(false);
        rb.setChecked(true);
    }
    @Override
    protected void onDestroy() {
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if(mHander != null){
            mHander.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
    Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case R.id.rb_home:
                    setCheck(rb_home);
                    break;
                case R.id.rb_mine:
                    setCheck(rb_mine);
                    if(index == 1){
                        mHander.sendEmptyMessageDelayed(1003,200);
                    }else if(index == 2){
                        mHander.sendEmptyMessageDelayed(1004,200);
                    }
                    break;
//                case 1003:
//                    index = -1;
//                    Intent intent_wait_pay = new Intent(MainActivity.this,OrderListActivity.class);
//                    intent_wait_pay.putExtra("index",1);
//                    startActivity(intent_wait_pay);
//                    break;
//                case 1004:
//                    index = -1;
//                    Intent intentalread_pay = new Intent(MainActivity.this,OrderListActivity.class);
//                    intentalread_pay.putExtra("index",2);
//                    startActivity(intentalread_pay);
//                    break;
            }
        }
    };
    @Override
    protected void setLayouts() {
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void initViews() {
        isInCreate = getIntent().getBooleanExtra("isOnCreate",false);
        radioButtonId = rb_home;
        CacheUtils.getInstance().saveShared("versionCode", NormalUtils.getLocalVersionCode()+"");
        fm = getSupportFragmentManager();
        f_home = new HomeFrament();
        f_consult = new ConsultFrament();
        f_trade = new TradeFrament();
        f_mine = new MineFrament();
        mFragments.add(f_home);
        mFragments.add(f_consult);
        mFragments.add(f_trade);
        mFragments.add(f_mine);
        rb_home.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioButtonId = rb_home;
                    showBottomFrament(0);
                    setCheck(radioButtonId);
                }
            }
        });
        rb_consult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioButtonId = rb_consult;
                    currClikcButtonId = -1;
                    showBottomFrament(1);
                    setCheck(radioButtonId);
                }
            }
        });
        rb_trade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioButtonId = rb_trade;
                    showBottomFrament(2);
                    setCheck(radioButtonId);
                }
            }
        });
        rb_mine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioButtonId = rb_mine;
                    showBottomFrament(4);
                    setCheck(radioButtonId);
                }
            }
        });
        showBottomFrament(0);
    }
    long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
                    NormalUtils.customShowToast(getResources().getString(R.string.main_finish));
                    firstTime = secondTime;
                    return true;
                } else {
                    NormalUtils.sendBroadcast(ActionFlag.CLOSEALL,null);
                    onBackPressed();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
    /**
     *  Frament得显示隐藏
     * @param position 跳转到的Frament的位置
     */
    public void showBottomFrament(int position){
        FragmentTransaction transaction = fm.beginTransaction();
        if(mFragments.size() > position){
            try{
                Fragment to = mFragments.get(position);
                if (currFrament != to) {
                    if (!to.isAdded()) {
                        if(currFrament != null){
                            transaction.hide(currFrament);
                        }
                        transaction.add(R.id.f_frament, to).commit();
                    } else {
                        if(currFrament != null){
                            transaction.hide(currFrament);
                        }
                        transaction.show(to).commit();
                    }
                    currFrament = to;
                }
            }catch (Exception e){
                NormalUtils.LogI(e.toString());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == Constants.REQUEST_CODE_SCAN) {
            if(data != null){
                Message msg = Message.obtain();
                msg.what = 10011;
                msg.obj = data.getStringExtra(Constant.CODED_CONTENT);
                mHandler.sendMessageDelayed(msg,500);
            }
        }
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
}
