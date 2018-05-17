package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetActivity extends AppCompatActivity {


    Button setEmail;

    EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        setEmail = findViewById(R.id.setEmail);
        Email = findViewById(R.id.forget_email);

        setEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                boolean isAt = false;
                for (int i = 0; i < email.length(); i++) {
                    if(email.charAt(i) == '@'){
                        isAt = true;
                    }
                }
                  if(email.isEmpty()){
                     Toast.makeText(ForgetActivity.this, "不填写邮箱就不能找回了喔", Toast.LENGTH_SHORT).show();
                }else {
                    if(!isAt){
                         Toast.makeText(ForgetActivity.this, "请输入正确的邮箱喔", Toast.LENGTH_SHORT).show();
                    }else{

                        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    Toast.makeText(ForgetActivity.this, "邮件已发送,请查看邮箱", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgetActivity.this,LoginActivity.class));
                                }else{
                                    Toast.makeText(ForgetActivity.this, "邮件发送失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

            }

        }
        });

    }
}
