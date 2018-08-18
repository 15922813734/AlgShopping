package com.alg.shopping.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.alg.shopping.ui.base.BaseActivity;
import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

/**
 * Created by XB on 2018/2/24.
 */
public class ZxingUtils {
    /**
     * @Override
     *
     *
     * 扫描二维码接受返回值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // 扫描二维码/条码回传
    if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
    if (data != null) {

    String content = data.getStringExtra(Constant.CODED_CONTENT);
    result.setText("扫描结果为：" + content);
    }
    }
    }
     * @param context
     */

    /**
     *
     * contentEtString：字符串内容
     * w：图片的宽
     * h：图片的高
     * logo：不需要logo的话直接传null
     *  try {
            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, logo);
        } catch (WriterException e) {
            e.printStackTrace();
        }
     * @param context
     */

    public static void jumpToZxing(Context context,int REQUEST_CODE_SCAN){
        Intent intent = new Intent(context, CaptureActivity.class);
        ZxingConfig config = new ZxingConfig();
        config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        config.setPlayBeep(true);//是否播放提示音
        config.setShake(true);//是否震动
        config.setShowAlbum(false);//是否显示相册
        config.setShowFlashLight(true);//是否显示闪光灯
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        ((BaseActivity)context).startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
    public static Bitmap generateQR(String contentEtString) throws WriterException {
        Bitmap bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, null);
        return bitmap;
    }
}
