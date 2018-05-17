package com.example.administrator.myapplication.localAPI;

import com.example.administrator.myapplication.APIBean.ButerfulBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.localAPI
 * 文件名：   PhotoApi
 * 创建者:    Nixo
 * 创建时间：  2018/4/23/023-14:37
 * 描述：https://www.apiopen.top/
 */

public interface PhotoApi {

    @GET("meituApi?page=1")
    Observable<ButerfulBean> ButerBack();


}
