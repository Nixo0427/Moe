package com.example.administrator.myapplication.utils;

import android.util.Log;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.utils
 * 文件名：   L
 * 创建者:    Nixo
 * 创建时间：  2018/2/20/020-2:11
 * 描述：      Log类
 */

public class L {


    public static boolean contril = true;
    public static String  TAG = "Smart";


    public void d (String text){
        if (contril){
            Log.d(TAG, text);
        }
    }
    public void e (String text){
        if (contril){
            Log.e(TAG, text);
        }
    }
    public void i (String text){
        if (contril){
            Log.i(TAG, text);
        }
    }
    public void w (String text){
        if (contril){
            Log.w(TAG, text);
        }
    }
}
