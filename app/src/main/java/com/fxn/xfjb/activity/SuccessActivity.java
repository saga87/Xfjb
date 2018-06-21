package com.fxn.xfjb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.xfjb.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessActivity extends AppCompatActivity {

    @Bind(R.id.iv_success_back)
    ImageView ivSuccessBack;
    @Bind(R.id.yzm)
    TextView yzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);

        try {
            String yzmname = getIntent().getStringExtra("yzm");
            yzm.setText(yzmname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_success_back, R.id.yzm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_success_back:
                finish();
                break;
            case R.id.yzm:
                break;
        }
    }
}
