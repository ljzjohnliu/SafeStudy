package com.safe.study.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String TAG = "Util";

    public static String getPids() {
        //获取当前进程ID
        int myPid = android.os.Process.myPid();
        //获取当前进程的用户ID
        int myUid = android.os.Process.myUid();
        //获取应用主线程ID
        long mainThreadId = Looper.getMainLooper().getThread().getId();
        //获取当前线程ID（1）
        long threadId1 = Thread.currentThread().getId();
        //Process.myTid() is the linux thread ID
        //线程ID,一个进程中可以有多个线程。每个进程有一个默认线程，称为主线程，通常为UI线程，其ID与进程ID相同
        int threadId2 = android.os.Process.myTid();
//        return "Print info : myPid = " + myPid + ", myUid = " + myUid + ", mainThreadId = "
//                + mainThreadId + ", threadId1 = " + threadId1 + ", threadId2 = " + threadId2;
        return "Print info : 进程名 = " + getProcessName() + ", 进程id = " + myPid + ", 主线程id = " + mainThreadId + ", 当前线程id = " + threadId1;
    }

    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkPhoneNumber(String phoneNum) {
        return phoneNum.length() == 11 && checkValid(phoneNum, "^1[0-9]{10}$");
    }

    public static boolean checkVerifyCode(String verifyCode) {
        return verifyCode.length() == 6 && checkValid(verifyCode, "[0-9]{6}$");
    }

    private static boolean checkValid(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static int randomCode() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

    /**
     * 毫秒时间戳转Date
     *
     * @param timestamp 13位的秒级别的时间戳
     * @return
     */
    public static String formatTime(double timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        return date;
    }

    public static String formatTime(String timestamp) {
        double time = Double.parseDouble(timestamp);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        return date;
    }

    public static int formatTimeToDay(long timestamp) {
        int days = (int) (timestamp / (24 * 60 * 60 * 1000));
        return days;
    }

    /**
     * String格式的日期转毫秒时间戳
     *
     * @param date
     * @param format
     * @return
     */
    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAppVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String getAppVedrsionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    public static int StringToInt(String str) {
        int val = Integer.parseInt(Pattern.compile("[^0-9]").matcher(str).replaceAll("").trim());
        return val;
    }


    public static final String[] constellationArray = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    public static final int[] constellationEdgeDay = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};

    //get Constellation by data
    public static String date2Constellation(int month, int day) {
        //int month = time.get(Calendar.MONTH);
        // int day = time.get(Calendar.DAY_OF_MONTH);
        //month from zero;
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArray[month];
        }
        // default to return 魔羯
        return constellationArray[11];
    }

    public static String floatToPercentage(float ratio) {
        DecimalFormat df = new DecimalFormat("0%");
        return df.format(ratio);
    }

    public static String rvZeroAndDot(float val) {
        String str = String.valueOf(val);

        if (str.indexOf(".") > 0) {

            // 去掉多余的0

            str = str.replaceAll("0+?$", "");

            // 如最后一位是.则去掉

            str = str.replaceAll("[.]$", "");

        }
        return str;
    }
}
