package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.utils
 * 文件名：   SharedUtil
 * 创建者:    Nixo
 * 创建时间：  2018/2/22/022-1:25
 * 描述：      Shared封装类
 */

public class SharedUtil {

    public static final String NAME = "Shared";

    public static void putString (Context mContext,String key,String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static String  getString (Context mContext , String key , String deafValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,deafValue);
    }


    public static void putInt (Context mContext , String key , int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    public static int getInt (Context mContext , String key , int deafAValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key , deafAValue);
    }

    public static void putBoolean (Context mContext , String key , boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean  (Context mContext , String key , boolean deafValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,deafValue);
    }

    public static void delShared (Context mContext , String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key);
    }

    public static void delALL (Context mContext){
        SharedPreferences sp  = mContext .getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear();
    }

}
