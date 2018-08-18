package com.alg.shopping.ui.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alg.shopping.R;
import com.alg.shopping.bean.EventBean;
import com.alg.shopping.contants.ActionFlag;
import com.alg.shopping.tools.DialogUtils;
import com.alg.shopping.tools.NormalUtils;
import com.alg.shopping.tools.PermissionsChecker;
import com.alg.shopping.tools.StatusBarHelper;
import com.alg.shopping.ui.logo.LogoActivity;
import com.alg.shopping.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by QiuQiu on 2017/11/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static Typeface typeface;
    private static final int PERMISSION_REQUEST_CODE_ONE = 1; // 系统权限管理页面的参数
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean isRequireCheck; // 是否需要系统权限检测
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (typeface == null)
//        {
//            typeface = Typeface.createFromAsset(getAssets(), "msyhl.ttc");
//        }
//        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory()
//        {
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs)
//            {
//                AppCompatDelegate delegate = getDelegate();
//                View view = delegate.createView(parent, name, context, attrs);
//
//                if ( view!= null && (view instanceof TextView))
//                {
//                    ((TextView) view).setTypeface(typeface);
//                }
//                if(view!=null && (view instanceof EditText)){
//                    ((EditText) view).setTypeface(typeface);
//                }
//                return view;
//            }
//        });
        setLayouts();
        super.onCreate(savedInstanceState);
//        NormalUtils.initState(BaseActivity.this);
        StatusBarHelper.translucent(this,getResources().getColor(R.color.transelate));
        View rootview = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        if(rootview != null && !(this instanceof MainActivity)){
            rootview.setFitsSystemWindows(true);
        }
//        if(this instanceof TYZQHomeActivity || this instanceof MyGiftListActivity || this instanceof MapLocationActivity || this instanceof BigImgActivity || this instanceof GoodsDescActivity || this instanceof ShowWebActivity || this instanceof OffLineStoreMapActivity){
//            StatusBarHelper.setStatusBarLightMode(this);
//        }else{
//            StatusBarHelper.setStatusBarDarkMode(this);
//        }
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        isRequireCheck = true;
        if(!(this instanceof LogoActivity)){
            initViews();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时
        if (isRequireCheck) {
            if (PermissionsChecker.lacksPermissions(PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE_ONE);
            }else if(this instanceof LogoActivity){
                initViews();
            }
        }else{
            isRequireCheck = true;
        }
    }
    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        DialogUtils.normalDialog(BaseActivity.this, "提示", "当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。", "设置", "退出", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NormalUtils.sendBroadcast(ActionFlag.CLOSEALL,null);
            }
        });
    }
    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            if(this instanceof LogoActivity){
                initViews();
            }
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }
    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
    @Subscribe
    public void onEventMainThread(EventBean event) {
        if(TextUtils.equals(ActionFlag.CLOSEALL,event.getFlag())){
            finish();
        }else if((TextUtils.equals(event.getFlag(), ActionFlag.BACKHOME) || TextUtils.equals(event.getFlag(),ActionFlag.JUMPTOORDERADDRESS) || TextUtils.equals(event.getFlag(),ActionFlag.JUMPTOORDERAREADY) || TextUtils.equals(event.getFlag(),ActionFlag.GIFTBUYSUCCESS)) && !(this instanceof MainActivity)){
            finish();
        }
        reciverMesssage(event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    protected abstract void reciverMesssage(EventBean obj);
    protected abstract void setLayouts();
    protected  abstract void initViews();
    public void jumpPage(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_from_right, R.anim.fade_out_to_left);
    }

    public void jumpPage(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.fade_in_from_right, R.anim.fade_out_to_left);
    }
}
