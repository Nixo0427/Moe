package com.example.administrator.myapplication.entily;

import cn.bmob.v3.BmobUser;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.entily
 * 文件名：   MyUser
 * 创建者:    Nixo
 * 创建时间：  2018/2/26/026-16:51
 * 描述：      用户属性
 */

public class MyUser extends BmobUser{

    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
