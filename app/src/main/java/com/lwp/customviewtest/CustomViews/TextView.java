package com.lwp.customviewtest.CustomViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lwp.customviewtest.R;

/**
 * <pre>
 *     author : 李蔚蓬（简书_凌川江雪）
 *     time   : 2020/8/17 2:21
 *     desc   :
 * </pre>
 */
public class TextView extends LinearLayout {

    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;

    //写字用的画笔
    private Paint mPaint;

    //这个构造函数 会在代码里面 new的时候 调用
    //TextView textView = new TextView(this);
    public TextView(Context context) {
        this(context,null);
    }

    //这个构造函数 在布局layout中使用时候 调用
//    <com.lwp.customviewtest.CustomViews.TextView
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content" />
    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //这个构造函数 在布局layout中使用 并且有style的时候 调用
    // 【Refactor -- Extract -- style】
//    <com.lwp.customviewtest.CustomViews.TextView
//    android:text="6666666666666666666"
//    style="@style/cstyle" />
//   style可以提取重复的属性
    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性列表 TypedArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TextView);
        // 获取文本 有时候可能会找不到我们自定义的属性 如TextView_lwpMaxLength，这个时候Rebuild一下，或者重启AS即可
        mText = (String) typedArray.getText(R.styleable.TextView_lwpText);
        // 获取文字颜色
        mTextColor = typedArray.getColor(R.styleable.TextView_lwpTextColor, mTextColor);
        // 获取文字大小
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_lwpTextSize, sp2px(mTextSize));

        // 回收
        typedArray.recycle();

        mPaint = new Paint();
        //抗锯齿 画的时候不会那么模糊
        mPaint.setAntiAlias(true);
        //设置 字体的大小和颜色！！
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        //思路2：默认给一个背景
//        setBackgroundColor(Color.TRANSPARENT);

        //思路3：
        setWillNotDraw(false);
    }

    /**
     * 自定义View的测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的 宽高 都是由这个方法指定
        //指定控件的宽高 需要测量

        //获取宽高的模式 widthMeasureSpec的前两位
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();

        //1.确定的值，这个时候 不需要计算，给的多少就是多少
        //获取宽高的值 widthMeasureSpec的后30位
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        //2.给的是wrap_content，需要计算
        if (widthMode == MeasureSpec.AT_MOST) {
            //计算的宽度 与 字体的长度、大小 有关  用画笔来测量
            Rect bounds = new Rect();
            //获取文本的 Rect
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            //拿到文本的宽度
            widthSize = bounds.width() + getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.AT_MOST) {

            int x = getPaddingLeft();
            //计算的宽度 与 字体的长度、大小 有关  用画笔来测量
            Rect bounds = new Rect();
            //获取文本的 Rect
            mPaint.getTextBounds(mText, x, mText.length(), bounds);
            //拿到文本的高度
            heightSize = bounds.height() + getPaddingTop() + getPaddingBottom() -
                    fontMetricsInt.ascent + fontMetricsInt.descent;
        }

        //设置控件的宽高
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //***********************************计算基线************************************
        //画文字 四个参数：text内容  x  y  paint画笔
        //x 开始的位置 0   y 基线
        //dy 代表的是：高度的一半到 baseline的距离
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;
        //*******************************************************************************

//        canvas.drawText(mText, 0, getHeight() / 2, mPaint);
        canvas.drawText(mText, 0, baseline, mPaint);
    }

    //sp转px
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
