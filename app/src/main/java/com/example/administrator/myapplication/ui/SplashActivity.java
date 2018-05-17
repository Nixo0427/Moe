package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entily.MyUser;
import com.example.administrator.myapplication.utils.L;
import com.example.administrator.myapplication.utils.SharedUtil;
import com.example.administrator.myapplication.utils.StaticClass;
import com.example.administrator.myapplication.utils.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SplashActivity extends AppCompatActivity {


    /**
     * 1.延迟2000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     * @param savedInstanceState
     */


    @BindView(R.id.tv_splash)
    TextView tvSplash;
    L l = new L();
    SharedUtil util = new SharedUtil();


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否第一次运行
                    if ( isFirst()) {
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        //loginActivity
                        if(BmobUser.getCurrentUser() == null){
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }else{
                            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                            myUser.setUsername(util.getString(SplashActivity.this,"name",""));
                            myUser.setPassword(util.getString(SplashActivity.this,"password",""));
                            myUser.login(new SaveListener<MyUser>() {
                                @Override
                                public void done(MyUser myUser, BmobException e) {
                                    if(e == null){
                                        Toast.makeText(SplashActivity.this, "欢迎回来,Master~", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SplashActivity.this,MainActivity.class ));
                                    }else{

                                        Toast.makeText(SplashActivity.this, "发生未知错误" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    }
                                }
                            });

                        }

                    }
                    finish();
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Message message = handler.obtainMessage();
        message.what = StaticClass.HANDLER_SPLASH;
        handler.sendMessage(message);

    }



    public boolean isFirst() {
        boolean isFirst = SharedUtil.getBoolean(this,StaticClass.SHARE_IS_FRIST,true);
        if (isFirst){
            SharedUtil.putBoolean(this,StaticClass.SHARE_IS_FRIST,false);
            return true;
        }else{
            return false;
        }

    }
}
