package com.example.administrator.myapplication.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.myapplication.APIBean.ButerfulBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.GrilAdapter;
import com.example.administrator.myapplication.interfaceUtils.GrilItemClickListener;
import com.example.administrator.myapplication.localAPI.PhotoApi;
import com.example.administrator.myapplication.utils.RetrfulUtil;
import com.example.administrator.myapplication.view.LoginDialog;
import com.example.administrator.myapplication.view.NixoImageView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Action;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.fragment
 * 文件名：   GrilFragment
 * 创建者:    Nixo
 * 创建时间：  2018/2/17/017-23:59
 * 描述：     meituApi?page=1
 */

public class GrilFragment extends Fragment{

    View view;
    RecyclerView recyclerView;
    ArrayList<ButerfulBean.DataBean> arrayList = new ArrayList<>();
    private GrilItemClickListener listener;
    private LoginDialog dialog;
    private NixoImageView grilDialogItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.girlfragfment,null);
        recyclerView = view.findViewById(R.id.gril_recy);
        dialog = new LoginDialog(getActivity(),
                300,
                300,
                R.layout.dialog_gril_item,
                R.style.Dialog_Style,
                Gravity.CENTER,
                R.style.Login_Style);

        grilDialogItem = dialog.findViewById(R.id.dialog_gril_img);

        listener = new GrilItemClickListener() {
            @Override
            public void onItemClickListener(Uri uri) {
                Glide.with(getActivity())
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(grilDialogItem)

                ;
                dialog.show();
            }
        };
        initData();
        return view;
    }

    private void initData() {
        RetrfulUtil retrfulUtil = new RetrfulUtil();
        PhotoApi api = retrfulUtil.getObservable("https://www.apiopen.top/");
        api.ButerBack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ButerfulBean>() {
                    @Override
                    public void accept(ButerfulBean buterfulBean) throws Exception {
                        for (int i = 0; i < buterfulBean.getData().size(); i++) {
                            ButerfulBean.DataBean bean = buterfulBean.getData().get(i);
                            arrayList.add(bean);
//                            Log.i("JSY", "accept: " +bean.getUrl());
                        }
                    }
                });

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        GrilAdapter adapter= null;
        if(adapter == null){
            adapter = new GrilAdapter(arrayList,view.getContext(),listener);
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }


    }







}
