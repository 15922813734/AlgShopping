package com.alg.shopping.tools;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.compat.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alg.shopping.R;
import com.alg.shopping.bean.EventBean;
import com.alg.shopping.config.CusClickableSpan;
import com.alg.shopping.config.LocalImageHolderView;
import com.alg.shopping.ui.base.BaseActivity;
import com.alg.shopping.ui.login.LoginActivity;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/12/13.
 */

public class NormalUtils {
    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     *
     * @param apkPath
     * @return 安装成功返回true，安装失败返回false。
     */
    public static void installRoot(final String apkPath, final Activity mAct) {
        new AsyncTask<Boolean, Object, Boolean>() {
            @Override
            protected Boolean doInBackground(Boolean... params) {
                boolean result = false;
                DataOutputStream dataOutputStream = null;
                BufferedReader errorStream = null;
                try {
                    // 申请su权限
                    Process process = Runtime.getRuntime().exec("su");
                    dataOutputStream = new DataOutputStream(process.getOutputStream());
                    // 执行pm install命令
                    String command = "pm install -r " + apkPath + "\n";
                    dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
                    dataOutputStream.flush();
                    dataOutputStream.writeBytes("exit\n");
                    dataOutputStream.flush();
                    int i = process.waitFor();
                    if (i == 0) {
                        result = true; // 正确获取root权限
                    } else {
                        result = false; // 没有root权限，或者拒绝获取root权限
                    }
                } catch (Exception e) {
                    NormalUtils.LogI(e.toString());
                } finally {
                    try {
                        if (dataOutputStream != null) {
                            dataOutputStream.close();
                        }
                        if (errorStream != null) {
                            errorStream.close();
                        }
                    } catch (IOException e) {
                        NormalUtils.LogI(e.toString());
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(Boolean hasRoot) {
                if (!hasRoot) {
                    installAuto(apkPath, mAct);
                } else {
                    NormalUtils.customShowToast("安装完成!");
                }
            }
        }.execute();
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                NormalUtils.customShowToast("请打开获取手机信息权限");
                return null;
            }
            String imei = telephonyManager.getDeviceId();
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 自动安装
     *
     * @param apkPath
     */
    public static void installAuto(String apkPath,Context context) {
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        /**
         * Android7.0+禁止应用对外暴露file://uri，改为content://uri；具体参考FileProvider
         */
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, "com.tg.shopping.inter.MyFileProvider", new File(apkPath));
            localIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(new File(apkPath));
        }
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive"); //打开apk文件
        ((BaseActivity)context).jumpPage(localIntent);
    }
    public static void initBanner(ConvenientBanner mConvenientBanner, List<Object> imageList){
        mConvenientBanner.setCanLoop(true);
        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, imageList);
        mConvenientBanner.setPointViewVisible(true);
        mConvenientBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        mConvenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        mConvenientBanner.setManualPageable(true);
        mConvenientBanner.setCanLoop(false);
        if(imageList != null && imageList.size() > 0){
            mConvenientBanner.getViewPager().setOffscreenPageLimit(imageList.size());
        }
    }
    public static void showTranst(float ff, Context context) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = ff;
        ((Activity) context).getWindow().setAttributes(lp);
    }
    /**
     * 修改状态栏为全透明
     * @param act
     */
    public static void initState(Activity act) {
        if(act != null && !act.isFinishing()){
            Window window = act.getWindow();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                if(act instanceof MainActivity){
//                    window.setStatusBarColor(act.getResources().getColor(R.color.colorwhite));
//                }else{
                    window.setStatusBarColor(act.getResources().getColor(R.color.transelate));
//                }
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
            }
        }
    }
    public static CharSequence homeFirstBig(String str){
        String strIndex = "";
        CharSequence resultStr = "";
        if(!TextUtils.isEmpty(str)){
            String[] contentArray = str.split(" ");
            for (int i = 0; i < contentArray.length; i++) {
                String tempStr = contentArray[i];
                tempStr = tempStr.toUpperCase();
                if(!TextUtils.isEmpty(tempStr)){
                    String firstStr = tempStr.substring(0,1);
                    String otherStr = tempStr.substring(1,tempStr.length());
                    if(i != (contentArray.length - 1)){
                        strIndex = strIndex+"<font color='#000000'><big><big>"+firstStr+"</big></big></font>"+otherStr+" ";
                    }else{
                        strIndex = strIndex+"<font color='#000000'><big><big>"+firstStr+"</big></big></font>"+otherStr;
                    }
                }
            }
            resultStr = Html.fromHtml(strIndex);
        }else{
            resultStr = str;
        }
        return resultStr;
    }



    /**
     * 获得当前进程的名字
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        if (activity != null) {
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
    /**
     * 此方法只是关闭软键盘
     *
     */
    public static void hintKbTwo(Context context) {
        try{
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            BaseActivity act = (BaseActivity) context;
            imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e){}
    }
    /**
     * 日志
     * @param msg 打印信息
     */
    public static void LogI(String msg) {
        if (!BuildConfig.DEBUG) {
            Log.i(x.app().getString(R.string.app_name), msg);
        }
    }
    public static void customShowToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(x.app(), msg, Toast.LENGTH_SHORT).show();
        }
    }
    public static void setCusText(TextView tv_str, ArrayList<String> arrayStr, ArrayList<CusClickableSpan.spanOnClick>  mSpanOnClicks, ArrayList<Integer> colors, ArrayList<Float> textsizes){
        tv_str.setText("");
        for (int i = 0; i < arrayStr.size(); i++) {
            String str = arrayStr.get(i);
            SpannableString ssPan = new SpannableString(str);
            CusClickableSpan.spanOnClick mSpanOnClickItem= null;
            if(mSpanOnClicks != null && mSpanOnClicks.size() > i){
                mSpanOnClickItem = mSpanOnClicks.get(i);
            }
            int colotItem = tv_str.getContext().getResources().getColor(R.color.color_black);
            if(colors != null && colors.size() > i){
                colotItem = colors.get(i);
            }
            float textsizeItem = x.app().getResources().getDimension(R.dimen.text_midd_size);
            if(textsizes != null && textsizes.size() > i){
                textsizeItem = textsizes.get(i);
            }
            ssPan.setSpan(new CusClickableSpan(str,tv_str.getContext(),mSpanOnClickItem,colotItem,textsizeItem), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_str.append(ssPan);
        }
        tv_str.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public static String getDecmiTo(double totalPrice){
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(totalPrice);
    }
    public static void initWevViewSetting(WebView wv_view){
        WebSettings webSettings = wv_view.getSettings();
        wv_view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setDomStorageEnabled(true);//支持渲染
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//缓存模式
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
    }


    /**
     * 对目标Drawable 进行着色
     *
     * @param drawable 目标Drawable
     * @param color    着色的颜色值
     * @return 着色处理后的Drawable
     */
    public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    /**
     * 跳转到选择图片库
     * @param activity
     * @param maxNum
     * @param REQUEST_CODE_CHOOSE
     */
    public static void chooseImage(Activity activity,int maxNum,int REQUEST_CODE_CHOOSE) {
        Matisse.from(activity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .capture(false)  // 开启相机，和 captureStrategy 一并使用否则报错
                .captureStrategy(new CaptureStrategy(true,"com.tg.shopping.inter.MyFileProvider"))
                .maxSelectable(maxNum)
                .gridExpectedSize(DensityUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        String fileBase64 = "";
        if(file.exists() && file.isFile()){
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputFile.read(buffer);
            inputFile.close();
            fileBase64 = Base64.encodeToString(buffer,Base64.DEFAULT);
        }
        return fileBase64;
    }
    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName() {
        String localVersion = "";
        try {
            PackageInfo packageInfo = x.app().getPackageManager().getPackageInfo(x.app().getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersionCode() {
        int localVersionCode = 0;
        try {
            PackageInfo packageInfo = x.app().getPackageManager().getPackageInfo(x.app().getPackageName(), 0);
            localVersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersionCode;
    }
    /**
     * 吧电话号码中间显示为*号
     * @param phone
     * @return
     */
    public static String replacePhone(String phone){
        String phoneStr = "";
        if(AccountValidatorUtils.isMobile(phone)){
            phoneStr = phone.substring(0,3);
            for (int i = 0; i < phone.length()-7; i++) {
                phoneStr = phoneStr+"*";
            }
            phoneStr = phoneStr+phone.substring(phone.length()-4,phone.length());
        }
        return phoneStr;
    }

    /**
     * 银行卡号码中间显示为*号
     * @param cardNum
     * @return
     */
    public static String replaceCard(String cardNum){
        String phoneStr = "";
        if(!TextUtils.isEmpty(cardNum) && cardNum.length() > 8){
            phoneStr = cardNum.substring(0,4);
            for (int i = 0; i < cardNum.length()-8; i++) {
                phoneStr = phoneStr+"*";
            }
            phoneStr = phoneStr+cardNum.substring(cardNum.length()-4,cardNum.length());
        }
        return phoneStr;
    }
    public static CharSequence priceStyle(String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = "<font color='#FF554F'><small>&yen;</small><big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }
    public static CharSequence priceYellolwStyle(String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = "<font color='#FD8E44'><small>&yen;</small><big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }
    public static CharSequence priceWriteStyle(String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = "<font color='#FFFFFF'><small>&yen;</small><big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }
    public static CharSequence priceBlackStyle(String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = "<font color='#222222'><small>&yen;</small><big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }
    public static CharSequence priceBlackStyle(String descStr,String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = descStr+"<font color='#222222'><small>&yen;</small><big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }
    public static CharSequence priceBlackStyleType(String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = "<font color='#222222'>报单:&yen;<big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }
    public static CharSequence priceBlackStyleTypeOne(String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = "<font color='#666666'>手续费:&yen;<big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }
    public static CharSequence priceBlackStyleTypeOne(String descStr,String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = descStr+"<font color='#666666'><small>&yen;</small><big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }


    public static CharSequence priceStyle(String descStr,String price){
        try{
            price = new BigDecimal(price).toPlainString();
        }catch (Exception e){}
        String priceStr = descStr+"<font color='#FF554F'><small>&yen;</small><big>"+(TextUtils.isEmpty(price) ? "-" : price)+"</big></font>";
        return Html.fromHtml(priceStr);
    }

    /**
     * 发送广播
     * @param actionStr 标识符
     * @param obj 值
     */
    public static void sendBroadcast(String actionStr,Object obj){
        EventBean mEventBean = new EventBean();
        mEventBean.setFlag(actionStr);
        mEventBean.setObj(obj);
        EventBus.getDefault().post(mEventBean);
    }
    public static void jumpToLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        ((BaseActivity)context).jumpPage(intent);
    }
    /**
     * 功能描述：判断当前设备是否为模拟器
     * 参数：true=是模拟器   false-不是模拟器
     */
    public static boolean isEmulator() {
        //获取手机的Serial码
        String serial = Build.SERIAL;
        if (serial.equalsIgnoreCase("unknown") || serial.equalsIgnoreCase("android")) {
            return true;
        }
        return false;
    }
}
