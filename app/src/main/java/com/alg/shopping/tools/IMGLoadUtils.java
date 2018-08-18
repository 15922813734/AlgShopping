package com.alg.shopping.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alg.shopping.config.GlideRoundTransform;
import com.alg.shopping.ui.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by XB on 2018/2/23.
 */

public class IMGLoadUtils {
    /**
     * 加载普通图片 (剪切)
     *
     * @param data
     * @param view
     */
    public static void loadCenterCropPic(Object data, ImageView view, int drawableId) {
        Glide.with(x.app()).load(data).centerCrop().error(drawableId).into(view);
    }
    /**
     * 加载普通图片 (自适应)
     *
     * @param data
     * @param view
     */
    public static void loadMatrixPic(Object data, ImageView view, int drawableId) {
        Glide.with(x.app()).load(data).placeholder(drawableId).error(drawableId).into(view);
    }
    /**
     * 加载普通图片 (按宽度进行缩放)
     *
     * @param data
     * @param view
     */
    public static void loadScalePic(Object data, final ImageView view, int drawableId) {
        Glide.with(x.app()).load(data).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                try{
                    int imageWidth = resource.getWidth();
                    int imageHeight = resource.getHeight();
                    int height = DensityUtil.getScreenWidth() * imageHeight / imageWidth;
                    ViewGroup.LayoutParams para = view.getLayoutParams();
                    para.height = height;
                    para.width = DensityUtil.getScreenWidth();
                    view.setImageBitmap(resource);
                    Glide.with(x.app()).load(resource).asBitmap().into(view);
                }catch (Exception e){}
            }
        });
    }
    /**
     * 加载圆形图
     *
     * @param data
     * @param view
     */
    public static void loadUserHeadPic(int placeId, Object data, final ImageView view) {
        Glide.with(x.app()).load(data).asBitmap().centerCrop().placeholder(placeId).error(placeId).into(new BitmapImageViewTarget(view){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(x.app().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
    /**
     * 顶部圆角加载
     * @param placeId
     * @param data
     * @param imageview
     */
    public static void LoadCircleImageTwo(int placeId, Object data, ImageView imageview) {
        Glide.with(x.app()).load(data).placeholder(placeId).bitmapTransform(new MultiTransformation(
                new CenterCrop(x.app()),
                new RoundedCornersTransformation(x.app(), DensityUtil.dip2px(5), 0, RoundedCornersTransformation.CornerType.TOP))).error(placeId).into(imageview);
    }
    /**
     * 加载固定宽高圆角
     * @param placeId
     * @param data
     * @param imageview
     */
    public static void LoadCircleImage(int placeId, Object data, ImageView imageview,int width,int height) {
        Glide.with(x.app()).load(data).placeholder(placeId).override(width,height).transform(new CenterCrop(x.app()),new GlideRoundTransform(x.app(),5)).error(placeId).into(new GlideDrawableImageViewTarget(imageview){
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        });
    }
    /**
     * 加载自适应圆角
     * @param placeId
     * @param data
     * @param imageview
     */
    public static void LoadAdaptionImage(int placeId, Object data, ImageView imageview) {
        Glide.with(x.app()).load(data).placeholder(placeId).transform(new GlideRoundTransform(x.app(),5)).error(placeId).into(new GlideDrawableImageViewTarget(imageview){
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        });
    }
    /**
     * 圆角加载
     * @param placeId
     * @param data
     * @param imageview
     */
    public static void LoadCircleImage(int placeId, Object data, ImageView imageview) {
        Glide.with(x.app()).load(data).placeholder(placeId).transform(new CenterCrop(x.app()),new GlideRoundTransform(x.app(),5)).error(placeId).into(new GlideDrawableImageViewTarget(imageview){
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        });
    }
    public static String getRealFilePath(final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null )
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = x.app().getContentResolver().query(uri, new String[] {MediaStore.Images.ImageColumns.DATA }, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int indexTemp = cursor.getColumnIndex("mq_external_cache");
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }else if(indexTemp > -1){
                        data = cursor.getString(indexTemp);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public static void cropImage(Context context, Uri uri, String filePathTemp2,int REQUEST_CROP){
        try{
//            context.grantUriPermission("com.android.camera",uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePathTemp2)));
            intent.putExtra("return-data", false);
            ((BaseActivity)context).startActivityForResult(intent, REQUEST_CROP);
        }catch (Exception e){
            NormalUtils.customShowToast("图片不能被访问");
        }
    }
}
