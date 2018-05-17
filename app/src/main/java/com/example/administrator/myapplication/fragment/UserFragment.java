package com.example.administrator.myapplication.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entily.MyUser;
import com.example.administrator.myapplication.ui.LoginActivity;
import com.example.administrator.myapplication.utils.L;
import com.example.administrator.myapplication.utils.SharedUtil;
import com.example.administrator.myapplication.utils.StaticClass;
import com.example.administrator.myapplication.view.CropActivity;
import com.example.administrator.myapplication.view.LoginDialog;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.administrator.myapplication.utils.StaticClass.CHARME_CODE;
import static com.example.administrator.myapplication.utils.StaticClass.PHOTO_FILE_COD;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.fragment
 * 文件名：   UserFragment
 * 创建者:    Nixo
 * 创建时间：  2018/2/18/018-0:00
 * 描述：
 * 目前相机有bug，暂停使用
 */

public class UserFragment extends Fragment implements View.OnClickListener{


    private Button LogOut;
    private EditText user;
    private EditText age;
    private EditText desc;
    private RadioButton boy;
    private RadioButton gril;
    private CircleImageView circleImageView ;
    private View view;
    private Button edit;
    private Button saveBtn;
    private Button cancel;
    private LinearLayout save;
    private boolean enable;
    private Uri headUri;
    private LoginDialog dialog;
    private Button photo_carme , photo_file;

    //工具类
    private L l = new L();
    private SharedUtil util = new SharedUtil();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userfragment,null);

//        ButterKnife.bind(view);
        dialog = new LoginDialog(getActivity(),R.layout.dialog_photo,R.style.Dialog_Style);
        initView();
        setPhotoWithHeand();
        controlView();
        initUser();
        return view;
    }

    private void controlView(){
        circleImageView.setOnClickListener(this);
        LogOut.setOnClickListener(this);
        edit.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        cancel.setOnClickListener(this);
        photo_carme.setOnClickListener(this);
        photo_file.setOnClickListener(this);

        enable = false;
        enableView(enable);
        save.setVisibility(View.GONE);


    }
    private void initView() {
        circleImageView = view.findViewById(R.id.user_img);
        user = view.findViewById(R.id.user_user);
        LogOut = view.findViewById(R.id.user_logout);
        age = view.findViewById(R.id.user_age);
        desc = view.findViewById(R.id.user_desc);
        boy = view.findViewById(R.id.user_boy);
        gril = view.findViewById(R.id.user_girl);
        edit = view.findViewById(R.id.user_edit);
        save = view.findViewById(R.id.user_save);
        saveBtn = view.findViewById(R.id.user_save_save);
        cancel = view.findViewById(R.id.user_save_cancel);
        photo_carme = dialog.findViewById(R.id.photo_camera);
        photo_file = dialog.findViewById(R.id.photo_file);



    }

    private void initUser() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        age.setText(userInfo.getAge()+"");
        user.setText(userInfo.getUsername());
        desc.setText(userInfo.getDesc());
        if(userInfo.isSex()){
            boy.setChecked(true);

        }else{
            gril.setChecked(true);


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_img:
                dialog.show();
                break;
            case R.id.user_logout:
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser(); // 现在的currentUser是null了
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.user_edit:
                enable = true;
                enableView(enable);
                save.setVisibility(View.VISIBLE);
                break;
            case R.id.user_save_save:

                    MyUser mMyUser = BmobUser.getCurrentUser(MyUser.class);
                    mMyUser.setUsername(user.getText().toString());
                    mMyUser.setAge(Integer.parseInt(age.getText().toString()));
                    mMyUser.setSex(boy.getLinksClickable());
                    mMyUser.setDesc(desc.getText().toString());
                     if(
                                user.getText().toString().trim()!= null &&
                                age.getText().toString().trim() != null &&
                                desc.getText().toString().trim() != null&&
                                user.getText().toString().trim() != null
                        ){
                    mMyUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(getActivity(), "更新资料成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), "错误"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    save.setVisibility(View.GONE);
                    enable = false;
                    enableView(enable);


                    }else{
                         Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                     }
                     break;
            case R.id.user_save_cancel:
                save.setVisibility(View.GONE);
                enable = false;
                enableView(enable);
                initUser();
                break;

            case R.id.photo_camera:
//                setPhoto_carme();
                break;
            case R.id.photo_file:
                setPhoto_file();
                break;

        }
    }

    public void setPhoto_file(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_FILE_COD);
        dialog.dismiss();
    }
    public void enableView(boolean enable){
        user.setEnabled(enable);
        age.setEnabled(enable);
        desc.setEnabled(enable);
        boy.setEnabled(enable);
        gril.setEnabled(enable);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != getActivity().RESULT_CANCELED){
            switch (requestCode){
                //相机
                case CHARME_CODE:

                    File file = new File(Environment.getExternalStorageDirectory()+"/MoeImage/", StaticClass.IMAGE_NAME);
                    Intent i = new Intent(getActivity(), CropActivity.class);
                    i.putExtra("photo_uri",Uri.fromFile(file).toString());
                    startActivity(i);

                    break;
                //相册
                case PHOTO_FILE_COD:
                    Intent i2 = new Intent(getActivity(),CropActivity.class);
                    i2.putExtra("photo_uri",data.getData().toString());
                    startActivity(i2);

                    break;
            }
        }
    }



    private void setPhotoWithHeand(){

       String headUri = util.getString(getActivity(),"headbtm","");
       if (!headUri.isEmpty()){
           //利用Base64将String变为bitmap
           byte[] bytes = Base64.decode(headUri,Base64.DEFAULT);
           ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
           Bitmap bitmap =  BitmapFactory.decodeStream(stream);
           circleImageView.setImageBitmap(bitmap);

       }else{

       }
    }

//    public void setPhoto_carme() {
//        Intent intent = null;
//        // 判断存储卡是否可以用，可用进行存储
//
//        if (android.os.Environment.getExternalStorageState().equals(
//                android.os.Environment.MEDIA_MOUNTED)) {
//            //设定拍照存放到自己指定的目录,可以先建好
//            File file = new File(Environment.getExternalStorageState()+"/MoeImage/");
//            if(!file.exists()){
//                file.mkdirs();
//            }
//            Uri pictureUri;
//            File pictureFile = new File(Environment.getExternalStorageState()+"/MoeImage/", StaticClass.IMAGE_NAME);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                ContentValues contentValues = new ContentValues(1);
//                contentValues.put(MediaStore.Images.Media.DATA, pictureFile.getAbsolutePath());
//                pictureUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//            }else {
//                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                pictureUri = Uri.fromFile(pictureFile);
//            }
//            if (intent != null) {
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
//                startActivityForResult(intent, CHARME_CODE);
//            }
//        }
//
//        dialog.dismiss();
//    }



}
