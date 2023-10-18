package com.safe.study.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.safe.study.R;
import com.safe.study.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodbyeActivity extends BaseSimpleActivity {
    private static final String TAG = "GoodbyeActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye);
        ButterKnife.bind(this);
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