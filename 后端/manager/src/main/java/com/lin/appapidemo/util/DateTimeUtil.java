package com.lin.appapidemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateTimeUtil {
    public static String getDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    public static String getDateNumber(){
        return String.valueOf(System.currentTimeMillis()+15*86400000);
    }
    public static String getDateAfter15(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(System.currentTimeMillis()+15*86400000));
    }
    /**
     * 取得两个时间段的时间间隔
     * @param t1 时间1
     * @param t2 时间2
     * @return t2 与t1的间隔天数
     */
    public static int getBetweenDays(String t1,String t2) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 第一个时间大于第二个时间，说明没超期，返回0
        if(c1.after(c2)){
            return 0;
        }
        int betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR);
        for(int i=0;i<betweenYears;i++){
            c1.set(Calendar.YEAR,(c1.get(Calendar.YEAR)+1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    /**
     * 续借两周
     * @param time
     * @return
     * @throws ParseException
     */
    public static String addtime14(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();//获得一个Calendar类型的通用对象
        ca.setTime(format.parse(time));
        ca.add(Calendar.DATE, 14);
        Date enddate = ca.getTime(); //加上后的日期

        return format.format(enddate);
    }
    /**
     * 借书30天
     * @param time
     * @return
     * @throws ParseException
     */
    public static String addtime30(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();//获得一个Calendar类型的通用对象
        ca.setTime(format.parse(time));
        ca.add(Calendar.DATE, 30);
        Date enddate = ca.getTime(); //加上后的日期
        String lastdate=format.format(enddate);
        return lastdate;
    }
    /**
     *比较两个时间段
     */
    public static int compareTime(String t1,String t2)throws ParseException{
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 第一个时间大于第二个时间
        if(c1.after(c2)){
            return 0;
        }else{
            return 1;
        }
    }



    public final static String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    public final static String FORMAT_STRING2 = "EEE MMM dd yyyy HH:mm:ss z";

    public final static String[] REPLACE_STRING = new String[]{"GMT+0800", "GMT+08:00"};

    public final static String SPLIT_STRING = "(中国标准时间)";

    /**
     * 将js中获取的时间格式转化为自定义的时间戳格式
     */
    public static String parseTimeZone(String dateString) {
        try {
            dateString = dateString.split(Pattern.quote(SPLIT_STRING))[0].replace(REPLACE_STRING[0], REPLACE_STRING[1]);
            //转换为date
            SimpleDateFormat sf1 = new SimpleDateFormat(FORMAT_STRING2, Locale.ENGLISH);
            Date date = sf1.parse(dateString);
            return new SimpleDateFormat(FORMAT_STRING).format(date);
        } catch (Exception e) {
            throw new RuntimeException("时间转化格式错误" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + FORMAT_STRING + "]");
        }
    }

}
