package com.safe.study.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.safe.study.activity.GoodbyeActivity;
import com.safe.study.receiver.AdminReceiver;

import java.util.Calendar;

public class MyService extends Service {
    private static final String TAG = "MyService";
    int TIME_INTERVAL = 5000; //5s
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(TEST_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 复写onStartCommand()方法
     * 默认实现 = 将请求的Intent添加到工作队列里
     **/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: intent = " + intent);
        long taskTime = intent.getLongExtra("task_time", 0L);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent();
        alarmIntent.setAction(TEST_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        Log.d(TAG, "onStartCommand: taskTime = " + taskTime + ", triggerAtMillis = " + (taskTime - Calendar.getInstance().getTimeInMillis()));

        if (taskTime > 0) {
            long timeInterval = taskTime - Calendar.getInstance().getTimeInMillis();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0低电量模式需要使用该方法触发定时任务
//                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeInterval, pendingIntent);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeInterval, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上 需要使用该方法精确执行时间
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeInterval, pendingIntent);
            } else {//4.4以下，使用老方法
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), timeInterval, pendingIntent);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static final String TEST_ACTION = "com.safe.study" + "_TASK_ACTION";
    private ComponentName componentName;

    private void activeManager() {
        //使用隐式意图调用系统方法来激活指定的设备管理器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
        startActivity(intent);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: -------action = " + action);
            Intent byeIntent = new Intent(context, GoodbyeActivity.class);
            byeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(byeIntent);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    DevicePolicyManager dpm = (DevicePolicyManager)
                            getSystemService(Context.DEVICE_POLICY_SERVICE);
                    componentName = new ComponentName(getApplicationContext(),
                            AdminReceiver.class);
                    Log.d(TAG, "onReceive: -------isAdminActive = " + dpm.isAdminActive(componentName));
                    if (dpm.isAdminActive(componentName)) {//判断是否有权限(激活了设备管理器)
                        dpm.lockNow();// 直接锁屏
                        //杀死当前应用
//                    Process.killProcess(Process.myPid());
                    } else {
                        activeManager();//激活设备管理器获取权限
                    }
                }
            }).start();
//            if (TEST_ACTION.equals(action)) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
//                }
//            }
        }
    };
}

