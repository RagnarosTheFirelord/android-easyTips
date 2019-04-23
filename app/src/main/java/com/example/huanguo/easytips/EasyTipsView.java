package com.example.huanguo.easytips;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * tips控件
 */
public class EasyTipsView extends TextView {

    private int bgColor = Color.RED;

    private int mGravity = 0;  //  上左下右 0123 逆时针

    private float mScale = 0.3f; // 三角尖 距离比例  从左到右 从上到下

    private int mScaleHigh ; // 三角尖 高度

    private int mScaleWidth; // 三角尖 宽度




    private Paint mPaint;

    private int mCornor ;

    private RectF rectF;

    private Path mPath; // 三角形path

    private int padding;

    public EasyTipsView(Context context) {

        this(context,null);
    }

    public EasyTipsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);


    }

    public EasyTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttr(context,attrs);
    }


    private void resolveAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = attrs == null ? null : context.obtainStyledAttributes(attrs, R.styleable.EasyTipsView);
        if (typedArray != null) {
            bgColor = typedArray.getColor(R.styleable.EasyTipsView_tips_bg, 1);//背景
            mGravity = typedArray.getInteger(R.styleable.EasyTipsView_tips_gravity, 0);//箭头方向
            mScale = typedArray.getFloat(R.styleable.EasyTipsView_tips_scale, 0.3f);//text nor
            mScaleHigh = typedArray.getDimensionPixelOffset(R.styleable.EasyTipsView_tips_scale_high, 20);//text pre
            mScaleWidth = mScaleHigh * 2;
            padding = typedArray.getDimensionPixelOffset(R.styleable.EasyTipsView_tips_Padding, 15);//text pre
            mCornor = typedArray.getDimensionPixelOffset(R.styleable.EasyTipsView_tips_Cornor, 15);//text pre
            typedArray.recycle();

        }

        mPaint = new Paint();
        mPaint.setColor(bgColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        if (mGravity == 0) {
            setPadding(padding, padding + mScaleHigh, padding, padding);
        } else if (mGravity == 1) {
            setPadding(padding + mScaleHigh, padding, padding, padding);
        } else if (mGravity == 2) {
            setPadding(padding, padding, padding, padding + mScaleHigh);
        } else if (mGravity == 3) {
            setPadding(padding, padding, padding + mScaleHigh, padding);

        }


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mPath = new Path();
            if (mGravity == 0 || mGravity == 2) { // 上下
                rectF = new RectF(0, mGravity == 0 ? mScaleHigh : 0, w, mGravity == 0 ? h : h - mScaleHigh);
                float startW = mScale * w;
                float startH = mGravity == 0 ? mScaleHigh : (h - mScaleHigh);
                float endH = mGravity == 0 ? 0 : h;
                mPath.moveTo(startW, startH);
                mPath.lineTo(startW + mScaleHigh, endH);
                mPath.lineTo(startW + mScaleWidth, startH);
                mPath.close();
            } else if (mGravity == 1 || mGravity == 3) { // 左右
                rectF = new RectF(mGravity == 1 ? mScaleHigh : 0, 0, mGravity == 1 ? w : w - mScaleHigh, h);
                float startH = mScale * h;
                float startW = mGravity == 1 ? mScaleHigh : (w - mScaleHigh);
                float endW = mGravity == 1 ? 0 : w;
                mPath.moveTo(startW, startH);
                mPath.lineTo(endW, startH + mScaleHigh);
                mPath.lineTo(startW, startH + mScaleWidth);
                mPath.close();

            }


        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();


        canvas.drawPath(mPath, mPaint);


        canvas.drawRoundRect(rectF, mCornor, mCornor, mPaint);


        // 绘制三角

        canvas.restore();

        super.onDraw(canvas);


    }
}
