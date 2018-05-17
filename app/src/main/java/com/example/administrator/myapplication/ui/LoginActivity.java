package com.example.administrator.myapplication.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entily.MyUser;
import com.example.administrator.myapplication.utils.SharedUtil;
import com.example.administrator.myapplication.view.LoginDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{



    TextView registered;
    TextView forgetAccount;
    Button Login;
    EditText username;
    EditText password;
    CheckBox keep_password;
    LoginDialog dialog;
    String uriStr ;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uriStr = "android.resource://" + this.getPackageName() + "/"+R.raw.loading;
        uri= Uri.parse(uriStr);
        initView();



    }

    private void initView() {
        Login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgetAccount = findViewById(R.id.forgetAccount);
        forgetAccount.setOnClickListener(this);
        registered = findViewById(R.id.btn_registered);
        registered.setOnClickListener(this);
        Login.setOnClickListener(this);
        keep_password = findViewById(R.id.keep_password);

        dialog = new LoginDialog(LoginActivity.this,
                300,
                300,
                R.layout.dialog_loading,
                R.style.Dialog_Style,
                Gravity.CENTER,
                R.style.Login_Style);

        dialog.setCancelable(false);

        //设置记住密码选中状态
        boolean isCheck = SharedUtil.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheck);
        if(isCheck){
            username.setText(SharedUtil.getString(this,"name",""));
            password.setText(SharedUtil.getString(this,"password",""));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registered:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.forgetAccount:
                startActivity(new Intent(LoginActivity.this,ForgetActivity.class));
                break;
            case R.id.login:

               final String name =  username.getText().toString().trim();
               final String passwrd = password.getText().toString().trim();
               if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(passwrd)){
                   dialog.show();

                   //登陆

                   Log.i("JSY", dialog.isShowing()+"启动了");



                   final MyUser user = new MyUser();
                   user.setUsername(name);
                   user.setPassword(passwrd);
                   user.login(new SaveListener<MyUser>() {
                       @Override
                       public void done(MyUser myUser, BmobException e) {
                           dialog.dismiss();
                           if(e == null){

                               if (user.getEmailVerified()){
//                                   Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                   SharedUtil.putBoolean(LoginActivity.this, "keeppass",keep_password.isChecked());
                                   if(keep_password.isChecked()){
                                       SharedUtil.putString(LoginActivity.this,"name",name.trim());
                                       SharedUtil.putString(LoginActivity.this,"password",passwrd.trim());
                                   }else{
                                       SharedUtil.delShared(LoginActivity.this,"password");
                                   }
                                    finish();
                               }else{
                                   Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                               }
                           }else{
                               Toast.makeText(LoginActivity.this, "登录失败"+e.toString(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }else {
                   Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
               }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }
}
