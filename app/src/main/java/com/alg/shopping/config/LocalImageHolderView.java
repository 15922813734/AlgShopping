package com.alg.shopping.config;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.alg.shopping.R;
import com.alg.shopping.tools.IMGLoadUtils;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by Lenovo on 2017/12/16.
 */

public class LocalImageHolderView implements Holder<Object> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Object data) {
        IMGLoadUtils.loadCenterCropPic(data, imageView, R.mipmap.qr_code_pic);
    }
}
