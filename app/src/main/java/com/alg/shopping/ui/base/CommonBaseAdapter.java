package com.alg.shopping.ui.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by QiuQiu on 2017/11/18.
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas;
    private int layoutId = 0;
    public CommonBaseAdapter(Context context,List<T> datas,int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
    }
    public CommonBaseAdapter(Context context,List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }
    public CommonBaseAdapter(Context context) {
        this.mContext = context;
    }
    public void setData(List<T> datas){
        this.mDatas = datas;
    }
    public List<T> getData(){
        return mDatas;
    }
    public void addData(List<T> datas){
        if(mDatas != null){
            mDatas.addAll(datas);
        }else{
            mDatas = datas;
        }
    }
    public void setLayoutId(int layoutId){
        this.layoutId = layoutId;
    }
    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public  View getView(int position, View convertView, ViewGroup parent) {
        CommonBaseHolder holder = CommonBaseHolder.getInstance(mContext,layoutId, position, convertView, parent);
        convert(holder,position,mDatas.get(position));
        return holder.getConvertView();
    }
    /**
     * 填充holder里面控件的数据
     * @param holder
    * @param position
     * @param bean
     */
    public abstract void convert(CommonBaseHolder holder,int position,T bean);
}
