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

public class JudgeActivity extends AppCompatActivity {

    @Bind(R.id.btn_problem_wyjb)
    Button btnProblemWyjb;
    @Bind(R.id.btn_problem_nmjb)
    Button btnProblemNmjb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_problem_wyjb, R.id.btn_problem_nmjb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_problem_wyjb:
                Intent intent = new Intent(JudgeActivity.this, ReportProblemActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_problem_nmjb:
                Intent intent1 = new Intent(JudgeActivity.this, ProblemActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
