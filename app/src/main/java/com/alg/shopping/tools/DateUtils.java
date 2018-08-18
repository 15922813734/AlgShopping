package com.alg.shopping.tools;

import android.content.Context;
import android.text.TextUtils;

import com.alg.shopping.R;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Lenovo on 2017/12/19.
 */

public class DateUtils {

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        if(startDate != null && endDate != null){
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(startDate);
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
            fromCalendar.set(Calendar.MINUTE, 0);
            fromCalendar.set(Calendar.SECOND, 0);
            fromCalendar.set(Calendar.MILLISECOND, 0);
            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(endDate);
            toCalendar.set(Calendar.HOUR_OF_DAY, 0);
            toCalendar.set(Calendar.MINUTE, 0);
            toCalendar.set(Calendar.SECOND, 0);
            toCalendar.set(Calendar.MILLISECOND, 0);
            int num = (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
            if(num < 1){
                num = 1;
            }
            return num;
        }else{
            return 1;
        }
    }


    static TimePickerView pvCustomTime;
    public static TimePickerView initDatePicker(Context context, Calendar startCaledar, Calendar currCaledar, Calendar endCaledar, TimePickerView.OnTimeSelectListener listener) {
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(context, listener)
                .setDate(currCaledar)
                .setRangDate(startCaledar, endCaledar)
                .setContentSize(18)//滚轮文字大小
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(context.getResources().getColor(R.color.line_color))
                .build();
        return pvCustomTime;
    }
    static OptionsPickerView pvCustomOptions;
    /**
     * @description 注意事项：
     * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
     * 具体可参考demo 里面的两个自定义layout布局。
     */
    public static OptionsPickerView initOptionPicker(Context context, String[] data, int currSelect, OptionsPickerView.OnOptionsSelectListener onOptionsSelectListener) {//条件选择器初始化，自定义布局
        NormalUtils.hintKbTwo(context);
        pvCustomOptions = new OptionsPickerView.Builder(context, onOptionsSelectListener)
                .setContentTextSize(18)//滚轮文字大小
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setSelectOptions(currSelect)
                .setDividerColor(context.getResources().getColor(R.color.line_color))
                .build();
        if(data != null && data.length > 0){
            List<String> datas = Arrays.asList(data);
            pvCustomOptions.setPicker(datas);
            return pvCustomOptions;
        }
        return null;
    }
    /**
     * 判断是否为今天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws Exception {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDate(day,"yyyy-MM-dd");
        if(date != null){
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
                if (diffDay == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public static String decoverDateToStr(String day){
        String dayDesc = day;
        try {
            boolean isToday = IsToday(day);
            boolean isYesterday = IsYesterday(day);
            if(isToday){
                dayDesc = "今天";
            }else if(isYesterday){
                dayDesc = "昨天";
            }else{
                dayDesc = dateCoverStr(getDate(day),"yyyy.MM.dd");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayDesc;
    }
    /**
     * 判断是否为昨天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws Exception {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDate(day,"yyyy-MM-dd");
        if(date != null){
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
                if (diffDay == -1) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Date getDate(String time){
        Date mDate = null;
        if(!TextUtils.isEmpty(time)){
            try {
                mDate = new Date();
                mDate.setTime(Long.parseLong(time));
            }catch (Exception e){}
        }
        return mDate;
    }
    public static String dateCoverStr(Date mDate,String dateStyle) {
        String mDateStr = "";
        if (mDate != null) {
            SimpleDateFormat sf = new SimpleDateFormat(dateStyle);
            mDateStr = sf.format(mDate);
        }
        return mDateStr;
    }
    public static Date getDate(String dateStr,String currDateStr){
        SimpleDateFormat formatter = new SimpleDateFormat(currDateStr);
        try {
            return formatter.parse(dateStr);
        }catch (Exception e){

        }
        return null;
    }
    public static String dateStyleCover(String dateStr,String currDateStr,String coverDateStr){
        Date mDate = getDate(dateStr,currDateStr);
        return dateCoverStr(mDate,coverDateStr);
    }
}
