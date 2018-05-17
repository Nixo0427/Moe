package com.example.administrator.myapplication.application;

import android.app.Application;


import com.example.administrator.myapplication.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

import static com.example.administrator.myapplication.utils.StaticClass.BUGLY_KEY;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.application
 * 文件名：   BaseApplication
 * 创建者:    Nixo
 * 创建时间：  2018/2/17/017-22:57
 * 描述：
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), BUGLY_KEY, true);
        //初始化Bmob云
        Bmob.initialize(this, StaticClass.BMOB_KEY);
//


    }
}
