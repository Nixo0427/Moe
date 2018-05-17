package com.example.administrator.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entily.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_email;
    private RadioGroup mRadioGroup;
    private Button btnRegister;
    private boolean getGreand = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();


    }

    private void initView() {
        et_user = findViewById(R.id.et_user);
        et_age = findViewById(R.id.et_age);
        et_desc = findViewById(R.id.et_desc);
        et_pass = findViewById(R.id.et_pass);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_emil);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        btnRegister = findViewById(R.id.et_registered);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_registered:
                //获取到输入框的值
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) &
                        !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(pass) &
                        !TextUtils.isEmpty(password) &!TextUtils.isEmpty(password) ){

                    //判断两次密码输入是否相同
                    if (pass.equals(password)){
                        //先把性别判别一下
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if(checkedId == R.id.rb_boy){
                                    getGreand = true;
                                }else if (checkedId == R.id.rb_girl){
                                    getGreand = false;
                                }
                            }
                        });

                        if(TextUtils.isEmpty(desc)){
                            desc = "这个人很懒，什么都没有留下";
                        }

                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(getGreand);
                        user.setDesc(desc);


                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null){
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "注册失败"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this, "两次密码不同", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

        }
    }
}
