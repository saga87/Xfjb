package com.fxn.xfjb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.xfjb.R;
import com.fxn.xfjb.entity.ContextText;
import com.fxn.xfjb.model.InformModel;
import com.fxn.xfjb.unit.ICallBack;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fxn on 2017/11/12.
 */

public class JbcxFragment extends Fragment {
    View view;
    @Bind(R.id.iv_jbcx_back)
    ImageView ivJbcxBack;
    @Bind(R.id.et_jbcx_cxm)
    EditText etJbcxCxm;
    @Bind(R.id.btn_jbcx_cx)
    Button btnJbcxCx;
    @Bind(R.id.tv_jbcx_contect)
    TextView tvJbcxContect;
    private InformModel model;
    private ContextText text;

    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                try {
                    tvJbcxContect.setText(text.getInfo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (msg.what == 2) {
                Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jbcx, null);
        ButterKnife.bind(this, view);
        model = new InformModel();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_jbcx_back, R.id.et_jbcx_cxm, R.id.btn_jbcx_cx, R.id.tv_jbcx_contect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_jbcx_back:
                getActivity().finish();
                break;
            case R.id.et_jbcx_cxm:
                break;
            case R.id.btn_jbcx_cx:
                String jbcxcxm = etJbcxCxm.getText().toString();
                if(jbcxcxm.equals("")){
                    Toast.makeText(getContext(),"请填写查询码",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.appcxnr(jbcxcxm, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        text = (ContextText) object;
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                    @Override
                    public void error(Object object) {
                        Message message = Message.obtain();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                });
                break;
            case R.id.tv_jbcx_contect:
                break;
        }
    }
}
