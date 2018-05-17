package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.APIBean.ButerfulBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.interfaceUtils.GrilItemClickListener;

import java.util.ArrayList;

import butterknife.OnItemClick;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.adapter
 * 文件名：   GrilAdapter
 * 创建者:    Nixo
 * 创建时间：  2018/4/23/023-15:48
 * 描述：
 */

public class GrilAdapter extends RecyclerView.Adapter<GrilAdapter.ViewHolder> implements View.OnClickListener{


    private ArrayList<ButerfulBean.DataBean> arrayList;
    private Context context ;
    private GrilItemClickListener listener = null;


    public GrilAdapter(ArrayList<ButerfulBean.DataBean> arrayList, Context context , GrilItemClickListener listener){
            this.arrayList = arrayList;
            this.context = context;
            this.listener = listener;
        }

    @Override
    public GrilAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.gril_recy_item,parent,false);
        }
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GrilAdapter.ViewHolder holder, int position) {
        ButerfulBean.DataBean buterfulBean = arrayList.get(position);
        Glide.with(context).load(buterfulBean.getUrl()).into(holder.imageView);
        holder.itemView.setTag(buterfulBean.getUrl());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onClick(View v) {

        listener.onItemClickListener(Uri.parse((String) v.getTag()));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gril_recy_img);

        }



    }



}
