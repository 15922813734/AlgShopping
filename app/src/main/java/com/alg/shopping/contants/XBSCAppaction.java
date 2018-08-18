package com.alg.shopping.contants;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.multidex.MultiDexApplication;

import com.alg.shopping.config.CrashHandler;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/9.
 */

public class XBSCAppaction extends MultiDexApplication {
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorWhite, R.color.colorGray);//全局设置主题颜色
//                CustomRefreshHeader airPlusHeader = new CustomRefreshHeader(context);

//                ClassicsHeader mClassicsHeader = new ClassicsHeader(context);
//                mClassicsHeader.setSpinnerStyle(SpinnerStyle.Translate);
//                mClassicsHeader.setTimeFormat(new SimpleDateFormat("上次更新 MM-dd HH:mm", Locale.CHINA));
                return new ClassicsHeader(context).setTimeFormat(new SimpleDateFormat("上次更新 MM-dd HH:mm", Locale.CHINA));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }
    public static SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(x.app());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFA,
                    0x68, 0x68)));
            // set item width
            deleteItem.setWidth(DensityUtil.dip2px(50));
            deleteItem.setTitleColor(Color.WHITE);
            deleteItem.setTitle("删除");
            deleteItem.setTitleSize(12);
            // set a icon
//            deleteItem.setIcon(R.drawable.ic_delete);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//xutils初始化
        initDir();
        CrashHandler.getInstance().init(this);
    }
    private void initDir() {
        File file = new File(Constants.IMAGE_CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        File fileIMG = new File(Constants.DB_DATA_PATH);
        if (!fileIMG.exists()) {
            fileIMG.mkdirs();
        }
    }
}
