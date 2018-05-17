package com.example.administrator.myapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.BaseActivity;
import com.example.administrator.myapplication.utils.L;
import com.example.administrator.myapplication.utils.SharedUtil;
import com.example.administrator.myapplication.utils.StaticClass;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CropActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.cropImageView)
    CropImageView cropImage;

    @BindView(R.id.crop_save)
    Button save;

    @BindView(R.id.crop_cancel)
    Button cancel;

    L l = new L();

    Uri uri , saveUri ,CropUri;
    SharedUtil util = new SharedUtil();
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        EditPhoto(intent);
        saveUri =Uri.parse(Environment.getExternalStorageDirectory()+"/MoeImage/");

    }


    private void EditPhoto(Intent intent) {
        Uri uri = Uri.parse(intent.getStringExtra("photo_uri"));
        cropImage.crop(uri).execute(new CropCallback() {
            @Override
            public void onSuccess(Bitmap cropped) {
                Toast.makeText(CropActivity.this, "ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }
    private void SavePhoto(){
        cropImage.crop(uri)
                .execute(new CropCallback() {
                    @Override public void onSuccess(Bitmap cropped) {
                        cropImage.save(cropped)
                                .execute(saveUri, new SaveCallback() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        CropUri = uri;
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    }

                    @Override public void onError(Throwable e) {
                    }
                });
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imgString = new String(Base64.encodeToString(bytes,Base64.DEFAULT));
        util.putString(this,"headUri",imgString);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.crop_save:
                SavePhoto();
                finish();
                break;
            case R.id.crop_cancel:
                finish();
                break;
        }
    }
}