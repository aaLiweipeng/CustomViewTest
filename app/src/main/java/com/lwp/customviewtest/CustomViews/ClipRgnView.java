package com.lwp.customviewtest.CustomViews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lwp.customviewtest.R;

public class ClipRgnView extends View {

    private Bitmap mBitmap;
    public int clipWidth = 0;
    private int bitmapWidth;
    private int bitmapHeight;
    private static final int CLIP_HEIGHT = 30;
    //    private Region mRgn;
    private Path mPath;

    public ClipRgnView(Context context) {
        super(context);
        init();
    }

    public ClipRgnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

//        mRgn = new Region();
        mPath = new Path();
    }

    public void setDecodeSize(int bmpWidth, int bmpHeight) {
        mBitmap = decodeSampledBitmapFromResource(getResources(),R.drawable.testtheview,bmpWidth,bmpHeight);
        bitmapWidth = mBitmap.getWidth();
        bitmapHeight = mBitmap.getHeight();
    }

    public void reDraw() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mRgn.setEmpty();
        mPath.reset();

        int i = 0;//花了多少个矩形区域

        while (i * CLIP_HEIGHT <= bitmapHeight) {
            if (i % 2 == 0) {
//                mRgn.union(new Rect(0, i * CLIP_HEIGHT, clipWidth, (i + 1) * CLIP_HEIGHT));
                mPath.addRect(new RectF(0, i * CLIP_HEIGHT, clipWidth,
                        (i + 1) * CLIP_HEIGHT), Path.Direction.CCW);

            } else {
//                mRgn.union(new Rect(bitmapWidth - clipWidth, i * CLIP_HEIGHT, bitmapWidth, (i + 1) * CLIP_HEIGHT));
                mPath.addRect(new RectF(bitmapWidth - clipWidth,
                        i * CLIP_HEIGHT, bitmapWidth, (i + 1) * CLIP_HEIGHT), Path.Direction.CCW);
            }
            i++;
        }

        canvas.clipPath(mPath);
        canvas.drawBitmap(mBitmap, 0, 0, new Paint());

        if (clipWidth > bitmapWidth) {
            return;
        }

        clipWidth += 5;

        postInvalidate();
    }


    //下面两个方法用于进行图片压缩
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}
