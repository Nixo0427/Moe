package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.fragment
 * 文件名：   ButlerFragment
 * 创建者:    Nixo
 * 创建时间：  2018/2/17/017-23:59
 * 描述：
 */

public class ButlerFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.butlerfragment,null);
        return view;
    }
}
