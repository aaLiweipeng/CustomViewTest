package com.lwp.customviewtest.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CanvasTestView extends View {

    private int drawId;


    public CanvasTestView(Context context) {
        super(context);
        drawId = 0;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public CanvasTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawId = 0;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
        postInvalidate();
    }

    public void resetDrwaId() {
        drawId = 0;
        postInvalidate();
    }

    /**
     * 设置一个全局drawId
     * 通过点击事件更改drawId，并重绘
     * 重绘制时根据改变了的不同的drawId
     * 绘制不同的图像
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (drawId) {
            case 0:
                canvas.drawColor(Color.RED);
                canvas.save();
                break;
            case 1:
                canvas.drawColor(Color.RED);
                canvas.save();
                canvas.clipRect(new Rect(100, 100, 800, 800));
                canvas.drawColor(Color.GREEN);
                break;
            case 2:
                canvas.drawColor(Color.RED);
                canvas.save();
                canvas.clipRect(new Rect(100, 100, 800, 800));
                canvas.drawColor(Color.GREEN);
                canvas.restore();
                canvas.drawColor(Color.BLUE);
                break;

            case 3:
                draw1ColorAnd4Rect(canvas);
                break;
            case 4:
                draw1ColorAnd4Rect(canvas);

                canvas.restore();
                canvas.drawColor(Color.YELLOW);
                break;
            case 5:
                draw1ColorAnd4Rect(canvas);

                canvas.restore();
                canvas.restore();
                canvas.restore();
                canvas.drawColor(Color.YELLOW);
                break;

            case 6:
                draw1ColorAnd4Rect(canvas);
                break;

            case 7:
                draw1ColorAnd4RectWithCount(canvas);
                break;
        }
    }

    //抽取相同部分代码
    private void draw1ColorAnd4Rect(Canvas canvas) {
        canvas.drawColor(Color.RED);
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        canvas.save();

        canvas.clipRect(new Rect(200,200,700,700));
        canvas.drawColor(Color.BLUE);
        canvas.save();

        canvas.clipRect(new Rect(300,300,600,600));
        canvas.drawColor(Color.BLACK);
        canvas.save();

        canvas.clipRect(new Rect(400,400,500,500));
        canvas.drawColor(Color.WHITE);
    }

    //抽取代码
    private void draw1ColorAnd4RectWithCount(Canvas canvas) {
        canvas.drawColor(Color.RED);
        int c1 = canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        int c2 = canvas.save();

        canvas.clipRect(new Rect(200,200,700,700));
        canvas.drawColor(Color.BLUE);
        int c3 = canvas.save();

        canvas.clipRect(new Rect(300,300,600,600));
        canvas.drawColor(Color.BLACK);
        int c4  = canvas.save();

        canvas.clipRect(new Rect(400,400,500,500));
        canvas.drawColor(Color.WHITE);

        canvas.restoreToCount(c2);
        canvas.drawColor(Color.YELLOW);
    }
}
