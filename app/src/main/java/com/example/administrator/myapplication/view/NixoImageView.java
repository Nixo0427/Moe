package com.example.administrator.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import static android.media.MediaCodec.MetricsConstants.MODE;

/**
 * 项目名：   MyApplication5
 * 包名 ：    com.example.administrator.myapplication.view
 * 文件名：   NixoImageView
 * 创建者:    Nixo
 * 创建时间：  2018/5/1/001-14:22
 * 描述：      自定义ImageView，实现手势缩放功能
 * 1.将中心位置设置为图片中心
 * 2.进行手势缩放
 */

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 项目名：   MyImageViewTesrt
 * 包名 ：    com.example.administrator.myimageviewtesrt
 * 文件名：   NixoImageView
 * 创建者:    Nixo
 * 创建时间：  2018/5/1/001-16:20
 * 描述：
 */

public class NixoImageView extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener{

    //记录当前的状态 是拖拉图片，还是放大缩小;
    private int mode = 0;

    //拖拉模式
    private static final int MODE_DRAG = 1;

    //放大缩小模式
    private static final int MODE_ZOOM = 2;

    //手指离开模式
    private static final int MODE_LEAVE = 0;

    //用于记录开始时候的坐标位置
    private PointF startPoint = new PointF();

    //用于记录拖拉图片移动的坐标位置
    private Matrix matrix = new Matrix();

    //用于记录图片 要进行拖拉的前的坐标位置
    private Matrix currentMatrix = new Matrix();

    //两个手指的开始距离
    private float startDis;

    //两个手指的中间点
    private PointF midPoint;



    public NixoImageView(Context context) {
        this(context,null);
        setOnTouchListener(this);
    }

    public NixoImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        setOnTouchListener(this);
    }

    public NixoImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //event.getAction() & MotionEvent.ACTION_MASK保证了无限手指触控会出现的bug
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            // MotionEvent.ACTION_DOWN ：手指压下屏幕的情况
            case MotionEvent.ACTION_DOWN:
                mode = MODE_DRAG;
                //记录ImageView当前的移动位置
                //getImageMatrix()获取图片的矩阵
                currentMatrix.set(getImageMatrix());
                //设置起点坐标
                startPoint.set(event.getX(),event.getY());
                break;
            //手指在屏幕上移动，该事件就会被不断触发
            case MotionEvent.ACTION_MOVE:
                //拖拉图片
                if(mode == MODE_DRAG){
                    //x轴的移动距离 = 触摸点跟控件的距离 - 开始拖拉的横坐标；
                    float dx = event.getX() - startPoint.x;
                    //y轴的移动距离 = 触摸点跟控件的距离 - 开始拖拉的纵坐标；
                    float dy = event.getY() - startPoint.y;
                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx,dy);
                }
                //放大缩小图片
                else if(mode == MODE_ZOOM){
                    //结束距离
                    float enDis = distance(event);
                    if(enDis > 10f){ //如果两个手指距离大于10px
                        float scale = enDis / startDis;//得到缩放倍数
                        matrix.set(currentMatrix);
                        matrix.postScale(scale,scale,midPoint.x,midPoint.y);
                    }
                }
                break;
            //手指离开屏幕
            case MotionEvent.ACTION_UP:
                //当触点离开屏幕，但是屏幕上还有触点（手指）
            case MotionEvent.ACTION_POINTER_UP:
                mode = MODE_LEAVE;
                break;
            //当屏幕上已经有触点（手指） ,再有一个触点压下屏幕（也就是第二个手指压下屏幕）
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = MODE_ZOOM;
                //计算两个手指之间的距离
                startDis = distance(event);
                //计算两个手之间的中间点 ， 作为放大的中心
                if(startDis > 10f){
                    midPoint = mid(event);
                    currentMatrix.set(getImageMatrix());
                }
                break;
        }
        setImageMatrix(matrix);
        return true;
    }

    //计算连个手指间的距离
    private  float distance(MotionEvent event){
        float dx = (event.getX(1) - event.getX(0))/2;
        float dy = (event.getY(1) - event.getY(0))/2;
        return (float) Math.sqrt(dx*dx + dy*dy);
    }

    private PointF mid(MotionEvent event){
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return  new PointF(midX,midY);
    }


}
