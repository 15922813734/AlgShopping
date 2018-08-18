package com.alg.shopping.ui.logo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.alg.shopping.R;
import com.alg.shopping.bean.EventBean;
import com.alg.shopping.ui.base.BaseActivity;

/**
 * Created by XB on 2018/2/24.
 */

public class LogoActivity extends BaseActivity {
    final int MSG_FINISH_LAUNCHERACTIVITY = 1001;
    @Override
    protected void reciverMesssage(EventBean obj) {

    }

    @Override
    protected void setLayouts() {
        setContentView(R.layout.activity_logo);
    }

    @Override
    protected void initViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        SqlDao.getInance().insert(Constants.HTMLADDRESS,"{\"msg\":\"操作成功\",\"code\":1,\"data\":{\"gwxz\":\"http://39.107.76.196/ElephantShopping/agreement/gwxz\",\"fxjl\":\"http://39.107.76.196/ElephantShopping/agreement/fxjl\",\"num\":\"20033\",\"dpjb\":\"http://39.107.76.196/ElephantShopping/agreement/dpjb\",\"sjxz\":\"http://39.107.76.196/ElephantShopping/agreement/sjxz\",\"flsm\":\"http://39.107.76.196/ElephantShopping/agreement/flsm\",\"zhsm\":\"http://39.107.76.196/ElephantShopping/agreement/zhsm\",\"dplx\":\"http://39.107.76.196/ElephantShopping/agreement/dplx\",\"ysbh\":\"http://39.107.76.196/ElephantShopping/agreement/ysbh\",\"jf\":\"http://39.107.76.196/ElephantShopping/agreement/jf\",\"yhsyxy\":\"http://39.107.76.196/ElephantShopping/agreement/yhsyxy\",\"key\":\"MpIkGy2z1XXwU+5CoX20kVRMCpJSqeqlSYcFwvEszB18HaQnS7QGCMUhr1fgaBqKX6C1jH2c4T0d\\nm+8TiB+xopITospacjdwFU8NwUpeqLYrVj0n619kHHUadcR6YG3Hu7ESRZMih+Wcbw1PMCdcLY7w\\ngI6AA2O+4DdmRQu0VW0=\\n\",\"hytq\":\"http://39.107.76.196/ElephantShopping/agreement/hytq\"}}");
//        queryHtmlAddress();
//        StatService.start(this);
    }
    @Override
    protected void onDestroy() {
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
