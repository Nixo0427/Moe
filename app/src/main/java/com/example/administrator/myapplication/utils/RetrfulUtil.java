package com.example.administrator.myapplication.utils;

import android.content.Context;

import com.example.administrator.myapplication.localAPI.PhotoApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.utils
 * 文件名：   RetrfulUtil
 * 创建者:    Nixo
 * 创建时间：  2018/4/23/023-15:04
 * 描述：
 */

public class RetrfulUtil {

    //传入基础url，进行Retrofit的封装
    // 返回被观察者，

    public  PhotoApi getObservable(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        PhotoApi api = retrofit.create(PhotoApi.class);
        return api;

    }

}
