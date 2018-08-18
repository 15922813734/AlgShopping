package com.alg.shopping.ui.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by QiuQiu on 2017/11/18.
 */
public class CommonBaseHolder {
    private int mPosition;
    /*
     * 用于存储holder里面的各个view，此集合比map效率高,但key必须为Integer
     */
    private SparseArray<View> mViews;
    /**
     * 复用的view
     */
    private View convertView;

    private CommonBaseHolder(Context context, int position, int layoutId, ViewGroup parent) {
        this.mPosition = position;
        mViews = new SparseArray<>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
        convertView.setTag(this);
    }

    public static CommonBaseHolder getInstance(Context context,int layoutId,int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new CommonBaseHolder(context, position, layoutId, parent);
        } else {
            CommonBaseHolder holder = (CommonBaseHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

    }
    /**
     * 通过resourceId获取item里面的view
     * @param resourceId 控件的id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int resourceId) {
        View view = mViews.get(resourceId);
        if (view == null) {
            view = convertView.findViewById(resourceId);
            mViews.put(resourceId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }
    /**
     * 获取当前item的位置
     * @return
     */
    public int getPosition() {
        return mPosition;
    }
}
