package com.fxn.xfjb.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fxn.xfjb.R;
import com.fxn.xfjb.fragment.JbcxFragment;
import com.fxn.xfjb.fragment.JbznFragment;
import com.fxn.xfjb.fragment.WyjbFragment;

import java.util.ArrayList;
import java.util.List;

public class HeadActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rb_1,rb_2,rb_3;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        //初始化控件
        setView();
        //设置listener;
        setOnlistener();
        //设置Adapter
        setAdapter();

        Bundle bundle = getIntent().getExtras();
        String p = bundle.getString("page");
        if(p.equals("1")){
            viewPager.setCurrentItem(0);
        }
        if(p.equals("2")){
            viewPager.setCurrentItem(1);
        }
        if(p.equals("3")){
            viewPager.setCurrentItem(2);
        }
    }

    private void setAdapter() {
        fragments = new ArrayList<>();
        fragments.add(new JbznFragment());
        fragments.add(new WyjbFragment());
        fragments.add(new JbcxFragment());

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }
    private class  MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    private void setOnlistener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_3:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rb_1.setChecked(true);
                        break;
                    case 1:
                        rb_2.setChecked(true);
                        break;
                    case 2:
                        rb_3.setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setView() {
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        rb_2 = (RadioButton) findViewById(R.id.rb_2);
        rb_3 = (RadioButton) findViewById(R.id.rb_3);
    }
}
