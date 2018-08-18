package com.alg.shopping.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.alg.shopping.R;
import com.alg.shopping.ui.base.BaseActivity;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lenovo on 2017/12/16.
 */

public class DialogUtils {
    public static Dialog dialog;
    static Dialog mProgressDialog;//加载对话框
    /**
     * 展示加载对话框
     *
     * @param msg
     */
    public static void showLoadingDialog(Context context,String msg,boolean isCancleable) {
        try{
            if (mProgressDialog == null) {
                mProgressDialog = new Dialog(context, R.style.progress_dialog);
                mProgressDialog.setContentView(R.layout.submit_dialog_layout);
                mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            mProgressDialog.setCancelable(isCancleable);
            TextView msgTextView = mProgressDialog.findViewById(R.id.id_tv_loadingmsg);
            msgTextView.setText(msg);
            mProgressDialog.show();
        }catch (Exception e){}
    }
    /**
     * 取消加载对话框
     */
    public static void dismissLoadingDialog() {
        try{
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }catch (Exception e){}
    }
    public static void dissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            NormalUtils.hintKbTwo(x.app());
        }catch (Exception e){}
        dialog = null;
    }

    /**
     * 图片选择对话框
     * @param context
     * @param REQUEST_ALBUM_OK
     * @param REQUEST_CAMERA
     * @param filepath 拍照时的文件存储路径
     */
    public static void picChooseDialog(final Context context, final int REQUEST_ALBUM_OK, final int REQUEST_CAMERA, final String filepath, final int sizeNum) {
        dissDialog();
        try{
            final Dialog bottomDialog = new Dialog(context, R.style.loading_dialog);
            View view = View.inflate(context, R.layout.dialog_picchoose_layout, null);
            bottomDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            TextView tv_photo_album =  view.findViewById(R.id.tv_photo_album);
            View tv_camera = view.findViewById(R.id.tv_camera);
            TextView tv_cancle =  view.findViewById(R.id.tv_cancle);
            tv_photo_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FastClickCheck.check(view);
                    bottomDialog.dismiss();
                    if(REQUEST_ALBUM_OK == 20001){
                        NormalUtils.chooseImage((Activity) context,sizeNum,REQUEST_ALBUM_OK);
                    }else{
                        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //添加这一句表示对目标应用临时授权该Uri所代表的文件
                            albumIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        ((BaseActivity)context).startActivityForResult(albumIntent, REQUEST_ALBUM_OK);
                    }
                }
            });
            tv_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FastClickCheck.check(view);
                    bottomDialog.dismiss();
                    // 跳转到系统照相机
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
                        // 设置系统相机拍照后的输出路径
                        // 创建临时文件
                        try {
                            File mFile = new File(filepath);
                            if (mFile.exists()){
                                mFile.delete();
                            }
                            mFile.createNewFile();
                            Uri imageUri = null;
                            //判断当前Android版本号是否大于等于24
                            if (Build.VERSION.SDK_INT >= 24){
                                //如果是则使用FileProvider
                                imageUri = FileProvider.getUriForFile(context,"com.tg.shopping.inter.MyFileProvider", mFile);
                            } else {
                                //否则，使用原来的fromFile()
                                imageUri = Uri.fromFile(mFile);
                            }
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            ((BaseActivity)context).startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        NormalUtils.customShowToast("未获取到相机设备");
                    }

                }
            });
            tv_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FastClickCheck.check(view);
                    bottomDialog.dismiss();
                }
            });
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
            view.setLayoutParams(layoutParams);
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
            bottomDialog.getWindow().setWindowAnimations(R.style.mypopwindow_anim_style);
            bottomDialog.show();
        }catch (Exception e){}
    }
    /**
     *  通用对话框
     * @param context
     * @param titleStr title
     * @param contentStr 内容
     * @param sure 确认按钮文本
     * @param cancle 取消按钮文本
     * @param isDismiss 点击是否取消对话框
     * @param l 确认按钮点击监听
     * @param m 取消按钮点击监听
     */
    public static void normalDialog(Context context, String titleStr, String contentStr, String sure, String cancle, final boolean isDismiss, final View.OnClickListener l, final View.OnClickListener m) {
        dissDialog();
        try {
            dialog = new Dialog(context, R.style.loading_dialog);
            View view = View.inflate(context, R.layout.dialog_normal_layout, null);
            dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 璁剧疆甯冨眬
            TextView tv_title =  view.findViewById(R.id.tv_title);
            View tv_title_line = view.findViewById(R.id.tv_title_line);
            TextView tv_content =  view.findViewById(R.id.tv_content);
            if (TextUtils.isEmpty(titleStr)) {
                tv_title.setVisibility(View.GONE);
                tv_title_line.setVisibility(View.GONE);
            } else {
                tv_title.setVisibility(View.VISIBLE);
                tv_title_line.setVisibility(View.VISIBLE);
                tv_title.setText(titleStr);
            }
            tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
            tv_content.setText(contentStr);
            TextView tv_cancle =  view.findViewById(R.id.tv_cancle);
            tv_cancle.setText(cancle);
            TextView tv_sure = view.findViewById(R.id.tv_sure);
            tv_sure.setText(sure);
            tv_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FastClickCheck.check(view);
                    if (isDismiss) {
                        dialog.dismiss();
                    }
                    if (m != null) {
                        m.onClick(view);
                    }
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FastClickCheck.check(view);
                    if (isDismiss) {
                        dialog.dismiss();
                    }
                    if (l != null) {
                        l.onClick(view);
                    }
                }
            });
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = DensityUtil.getScreenWidth()- DensityUtil.dip2px(90);
            view.setLayoutParams(layoutParams);
            showDialog(false);
        }catch (Exception e){
            NormalUtils.customShowToast("图片选择器打开失败");
        }
    }
    private static void showDialog(boolean isCancleable){
        dialog.setCancelable(isCancleable);
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
        dialog.show();
    }
}
