package com.safe.study.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.safe.study.base.BaseSimpleActivity;
import com.safe.study.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseSimpleActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;

    @OnClick({R.id.set_task})
    public void onJumpClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.set_task:
                intent.setClass(this, SetTaskActivity.class);
                break;
        }
        startActivity(intent);
//        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int psNum = Runtime.getRuntime().availableProcessors();
        Log.d(TAG, "onCreate: psNum = " + psNum + ", thread id = " + Thread.currentThread().getId());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "------onNewIntent: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "------onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "------onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "------onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "------onDestroy: ");
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: ------------------");
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
}