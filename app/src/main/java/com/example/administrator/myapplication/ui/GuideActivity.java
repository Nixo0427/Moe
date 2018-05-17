package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.mViewpager)
    ViewPager mViewPager;
    @BindView(R.id.poi1)
    ImageView point1;
    @BindView(R.id.poi2)
    ImageView point2;
    @BindView(R.id.poi3)
    ImageView point3;


    ImageView ivback;
    List<View> mList = new ArrayList<>();

    View view1,view2,view3;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        init();

    }
    //初始化
    private void init() {
        ivback = findViewById(R.id.iv_back);
        ivback.setOnClickListener(this);
        view1 = View.inflate(this,R.layout.view_item_one,null);
        view2 = View.inflate(this,R.layout.view_item_two,null);
        view3 = View.inflate(this,R.layout.view_item_tree,null);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        setPoiImage(true,false,false);
        mViewPager.setAdapter(new GuideAdapter());


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //监听滑动返回结果
            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        setPoiImage(true,false,false);
                        ivback.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPoiImage(false,true,false);
                        ivback.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPoiImage(false,false,true);
                        ivback.setVisibility(View.GONE);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;


        }

    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager)container).removeView(mList.get(position));
        }
    }

    //设置小圆点的选中效果
    private  void setPoiImage(Boolean isCheck1 , Boolean isCheck2 , Boolean isCheck3){
        if(isCheck1){
            point1.setBackgroundResource(R.drawable.seco2);
        }else{
            point1.setBackgroundResource(R.drawable.seco);
        }

        if(isCheck2){
            point2.setBackgroundResource(R.drawable.seco2);
        }else{
            point2.setBackgroundResource(R.drawable.seco);
        }

        if(isCheck3){
            point3.setBackgroundResource(R.drawable.seco2);
        }else{
            point3.setBackgroundResource(R.drawable.seco);
        }

    }

}
