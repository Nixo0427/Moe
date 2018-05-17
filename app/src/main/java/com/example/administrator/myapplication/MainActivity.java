package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.administrator.myapplication.fragment.ButlerFragment;
import com.example.administrator.myapplication.fragment.GrilFragment;
import com.example.administrator.myapplication.fragment.UserFragment;
import com.example.administrator.myapplication.fragment.WechatFragment;
import com.example.administrator.myapplication.ui.SettingActivity;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tencent.bugly.crashreport.CrashReport.testJavaCrash;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @BindView(R.id.mDrawer) DrawerLayout drawerLayout;
    //TableLayout
    @BindView(R.id.mTablayout) TabLayout mTabLayout;
    //ViewPager
    @BindView(R.id.mViewpager) ViewPager mViewPager;

    @BindView(R.id.m_toolbar) android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.toolbar_serch) EditText serchDate;
    @BindView(R.id.toolbar_serch_Btn) Button serch;
    @BindView(R.id.nav_setting) Button mNavSettring;


    FloatingActionButton fab;

    //悬浮窗
//    @BindView(R.id.fab_setting)
//    FloatingActionButton fab;

    //Title
    private List<String> mTitles;
    //Fragment
    private List<Fragment> mFragments;


//    String serD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        mNavSettring.setOnClickListener(this);
//        serD = serchDate.getText().toString().trim();
        serch.setOnClickListener(this);
        initDate();
        initView();

        

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_serch_Btn:
                Toast.makeText(this, serchDate.getText().toString(), Toast.LENGTH_SHORT).show();
            break;
            case R.id.nav_setting:
                startActivity(new Intent(this,SettingActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                break;



        }
        return true;
    }









    private void initDate() {
      mTitles = new ArrayList<>();
      mTitles.add(getString(R.string.title1));
      mTitles.add(getString(R.string.title2));
      mTitles.add(getString(R.string.title3));
      mTitles.add(getString(R.string.title4));

      mFragments = new ArrayList<>();
      mFragments.add(new ButlerFragment());
      mFragments.add(new WechatFragment());
      mFragments.add(new GrilFragment());
      mFragments.add(new UserFragment());
    }


    private void initView() {

        fab = findViewById(R.id.fab_setting);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });

        //预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());

        //view滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "onPageSelected: ");
                if (position == 0){
                    fab.setVisibility(View.GONE);
                }else{
                    fab.setVisibility(View.VISIBLE);
//                    fab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
              //选中的item
              @Override
              public Fragment getItem(int position) {
                  return mFragments.get(position);
              }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragments.size();
            }

            //设置标题

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);

            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }



}
