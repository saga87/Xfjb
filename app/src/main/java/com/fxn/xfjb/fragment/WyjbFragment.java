package com.fxn.xfjb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.fxn.xfjb.R;
import com.fxn.xfjb.activity.ProblemActivity;
import com.fxn.xfjb.activity.ReportProblemActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fxn on 2017/11/12.
 */

public class WyjbFragment extends Fragment {
    View view;
    @Bind(R.id.iv_wyjb_back)
    ImageView ivWyjbBack;
    @Bind(R.id.btn_wyjb)
    Button btnWyjb;
    @Bind(R.id.btn_nmjb)
    Button btnNmjb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wyjb, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.iv_wyjb_back, R.id.btn_wyjb, R.id.btn_nmjb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wyjb_back:
                getActivity().finish();
                break;
            case R.id.btn_wyjb:
                Intent intent = new Intent(getActivity(), ReportProblemActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_nmjb:
                Intent intent1 = new Intent(getActivity(), ProblemActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
