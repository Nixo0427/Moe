package com.example.administrator.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.administrator.myapplication.R;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.view
 * 文件名：   LoginDialog
 * 创建者:    Nixo
 * 创建时间：  2018/4/25/025-13:19
 * 描述：
 */

public class LoginDialog extends Dialog {




    //这个是模板
    public LoginDialog(Context context,int layout , int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }

    //属性赋值
    public LoginDialog(Context context , int width , int height , int layout, int style,int gravity , int anim){
        super(context,style);

        //设置属性
        setContentView(layout);
        Dialog dialog = new Dialog(context);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(anim);

    }

    //创建实例
    public LoginDialog(Context context , int width , int height , int layout, int style,int gravity){
        this(context,width,height,layout,style,gravity,R.style.Login_Style);

    }

}
