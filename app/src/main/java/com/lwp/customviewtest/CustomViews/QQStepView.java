package com.lwp.customviewtest.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lwp.customviewtest.R;

/**
 * <pre>
 *     author : 李蔚蓬（简书_凌川江雪）
 *     time   : 2020/8/30 18:50
 *     desc   :
 * </pre>
 */
public class QQStepView extends View {

    //初始化 自定义属性变量 并给 默认值
    private int mOuterColor = Color.RED;//默认红色
    private int mInnerColor = Color.BLUE;//默认蓝色
    private int mBorderWidth = 20;//20px
    private int mStepTextSize = 20;
    private int mStepTextColor = Color.RED;

    private Paint mPaint;
    private int mStartAngle = 135;
    private int mSweepAngle = 270;

    private int mStepMax = 0;
    private int mCurrentStep = 0;


    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mOuterColor);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 调用者在布局文件中可能是 wrap_content 要做判断

        //获取宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //设置宽高
        // 宽高不一致 取最小值、确保是个正方形
        setMeasuredDimension(widthSize > heightSize ? heightSize : widthSize,
                widthSize > heightSize ? heightSize : widthSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画外圆弧==============================
        //拿到View的中心点
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        // 半径
        int radius = (int) (centerX - mBorderWidth);
        // 设置圆弧画笔的宽度
        mPaint.setStrokeWidth(mBorderWidth);
        // 设置弧线的 首端 和 尾端 为 圆弧
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        // 设置画笔颜色
        mPaint.setColor(mOuterColor);
        mPaint.setStyle(Paint.Style.STROKE);

        //圆弧 内边缘的弧线的 外切矩形
        RectF oval = new RectF(mBorderWidth, mBorderWidth, centerX + radius, centerY + radius);
        // 画背景圆弧
        canvas.drawArc(oval, mStartAngle, mSweepAngle, false, mPaint);

        //画内圆弧==============================
        // 提供百分比 给用户自己配置 【自定义属性】
        mPaint.setColor(mInnerColor);
        // 计算当前百分比
        float percent = (float) mCurrentStep / mStepMax;
        if (mStepMax != 0) {
            // 根据当前百分比计算圆弧扫描的角度
            canvas.drawArc(oval, mStartAngle, percent * mSweepAngle, false, mPaint);
        }

        //画文字==============================
        // 重置画笔
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mStepTextSize);
        mPaint.setColor(mStepTextColor);
        //文本内容
//        String mStep = ((int) (percent * mStepMax)) + "";
        String mStep = mCurrentStep + "";
        // 测量文字的宽高
        Rect textBounds = new Rect();
        mPaint.getTextBounds(mStep, 0, mStep.length(), textBounds);
        //文字的x轴起始点
        int dx = (getWidth() - textBounds.width()) / 2;
        // 获取画笔的FontMetrics
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        // 计算文字的基线
        int baseLine = (int) (getHeight() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        // 绘制步数文字
        canvas.drawText(mStep, dx, baseLine, mPaint);
    }

    // 设置当前最大步数
    public synchronized void setMaxStep(int maxStep) {
        if (maxStep < 0) {
            throw new IllegalArgumentException("max 不能小于0!");
        }
        this.mStepMax = maxStep;
    }
    public synchronized int getMaxStep() {
        return mStepMax;
    }
    // 设置进度
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress 不能小于0!");
        }
        this.mCurrentStep = progress;
        // 重新刷新绘制 -> onDraw()
        invalidate();
    }
    public synchronized int getProgress() {
        return mCurrentStep;
    }
}
