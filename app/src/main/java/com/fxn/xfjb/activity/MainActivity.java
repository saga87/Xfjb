package com.fxn.xfjb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fxn.xfjb.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_main_ybzn)
    Button btnMainYbzn;
    @Bind(R.id.btn_main_wyjb)
    Button btnMainWyjb;
    @Bind(R.id.btn_main_jbcx)
    Button btnMainJbcx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_main_ybzn, R.id.btn_main_wyjb, R.id.btn_main_jbcx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main_ybzn:
                Intent intent = new Intent(MainActivity.this,HeadActivity.class);
                intent.putExtra("page","1");
                startActivity(intent);
                break;
            case R.id.btn_main_wyjb:
                Intent intent1 = new Intent(MainActivity.this,HeadActivity.class);
                intent1.putExtra("page","2");
                startActivity(intent1);
                break;
            case R.id.btn_main_jbcx:
                Intent intent2 = new Intent(MainActivity.this,HeadActivity.class);
                intent2.putExtra("page","3");
                startActivity(intent2);
                break;
        }
    }
}
