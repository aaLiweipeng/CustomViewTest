package com.lwp.customviewtest.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class SpiderView extends View {

    /*
     * radar   美 [ˈredɑr]  n.雷达
     */
    private Paint radarPaint;//网格画笔
    private Paint valuePaint;//结果点值画笔
    private Paint textPaint;//末端标题画笔

    private float radius;//网格最大半径
    private float tempR;//用于计算和设置UI的临时变量
    private int centerX;//中心X
    private int centerY;//中心Y

    private int count;
    private float angle;

    private String[] titles;//某端点标题文本
    private double[] data;//各角数据
    private float maxValue;//最大值

    public SpiderView(Context context) {
        super(context);
        init();
    }

    public SpiderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        //网格画笔，描边
        radarPaint = new Paint();
        radarPaint.setStyle(Paint.Style.STROKE);
        radarPaint.setColor(Color.RED);
        radarPaint.setAntiAlias(true);

        //结果点值画笔，填充
        valuePaint = new Paint();
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStyle(Paint.Style.FILL);
        valuePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);

        //初始化数据
        data = new double[]{0.5, 1.5, 0.3, 6, 15, 6};
        maxValue = 6;
        titles = new String[]{"王者农药", "吃鸡", "剁手", "读书", "书法", "民乐"};
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //计算多边形半径
        radius = (float) Math.min(w, h) / 2 * 0.7f;
        //计算中心坐标
        centerX = w / 2;
        centerY = h / 2;

        //边数与角度
        count = 6;
//        angle = 60; //The bad operation!!!!!!——别干傻事
        angle = (float) (Math.PI * 2 / count);

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制蜘蛛网状图
        drawPolygon(canvas);

        //绘制网格中线
        drawLines(canvas);

        //绘制数据
        drawRegion(canvas);

        //绘制末端文本
        drawTexts(canvas);
    }

    private void drawTexts(Canvas canvas) {
        for (int i = 0; i < count; i++) {

            tempR = radius * 1.1f;//按比例归一化取值

            float x = (float) (centerX + tempR * Math.cos(angle * i));
            float y = (float) (centerY + tempR * Math.sin(angle * i));

            if (i == 0) {
                canvas.drawText(titles[i], x - 0.1f*tempR , centerY, textPaint);
            }else{
                canvas.drawText(titles[i], x , y, textPaint);
            }
        }
    }


    private void drawRegion(Canvas canvas) {
        Path path = new Path();

        valuePaint.setAlpha(127);

        for (int i = 0; i < count; i++) {
            float percent = (float) data[i] / maxValue;

            tempR = radius * percent;//按比例归一化取值

            float x = (float) (centerX + tempR * Math.cos(angle * i));
            float y = (float) (centerY + tempR * Math.sin(angle * i));

            if (i == 0) {
                path.moveTo(x, centerY);
            }else{
                path.lineTo(x, y);
            }

            //绘制小圆点
            canvas.drawCircle(x, y, 10, valuePaint);
        }
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

    private void drawLines(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < count; i++) {
            //注意每次画完一条线段，需要重置一下路径
            path.reset();

            path.moveTo(centerX, centerY);//以中心点为线段开始

            //找到各个末端点的坐标
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));

            path.lineTo(x, y);
            canvas.drawPath(path, radarPaint);
        }
    }

    //！！！！！！
    //排查之后发现问题不在这里，重点是angle的取值！！！！！！！！！！
    //！！！！！！
    private void drawPolygon(Canvas canvas) {

        Path path = new Path();

        float r = radius / count; //r 是网圈之间的间隔
        //逐个绘制网的单圈，从第1圈（一个r距离）开始画，直到count个，
        // 中心点（0个r距离）不用绘制
        for (int i = 1; i <= count; i++) {

            float curR = r * i;//当前 半径 / 距离中心的偏移量
            path.reset();//每一圈的开始，都要重置

            //从 0度 开始，绘制到  （count-1）* angle 度
            //  < count  ，即 （count-1）
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    //绘制 0度 的点 ，都是 0度 ，同一直线， 同在centerY
                    path.moveTo(centerX + curR, centerY);
                } else {
                    //根据半径，计算出蜘蛛网圈上的每一个点的坐标
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));

                    path.lineTo(x, y);
                }
            }
            //画完一圈点之后
            path.close();
            canvas.drawPath(path, radarPaint);//落实绘制路径
        }

    }

    //设置标题
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    //设置数值
    public void setData(double[] data) {
        this.data = data;
    }

    //设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    //设置蜘蛛网颜色
    public void setMainPaintColor(int color) {
        radarPaint.setColor(color);
    }

    //设置覆盖局域颜色
    public void setValuePaintColor(int color) {
        valuePaint.setColor(color);
    }

    //设置边数
    public void setCount(int count) {
        this.count = count;
    }

    public void setRadarPaint(Paint radarPaint) {
        this.radarPaint = radarPaint;
    }

    public void setValuePaint(Paint valuePaint) {
        this.valuePaint = valuePaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
