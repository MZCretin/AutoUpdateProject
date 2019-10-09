package com.cretin.www.cretinautoupdatelibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cretin.www.cretinautoupdatelibrary.R;


/**
 * Creatd by cretin on 2017/7/24.
 */

public class ProgressView extends View {
    //记录View的宽度
    private int mViewWidth = 0;
    //记录Progress的宽度
    private int mProgressWidth = 0;
    //记录View的高度
    private int mViewHeight = 0;
    private Context mContext;
    private float mScreenScale = 0;

    //进度条的头部资源图
    private Bitmap mProgressBitmap;
    //进度条背景资源图
    private Bitmap mProgressTipsBitmap;
    //画笔
    private Paint mPaint = new Paint();
    //计算每一步的步距
    private float mStepLength;
    //当前进度
    private int mProgress;

    public ProgressView(Context context) {
        this(context, null, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //设置进度
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }


    //初始化操作
    private void init(Context context) {
        mContext = context;
        mScreenScale = getScreenScale(context);

        //引入本地资源图片
        mProgressBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.progress);
        mProgressTipsBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.progress_tips);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //首先绘制一条背景图
        RectF mTopDestRect = new RectF(18 * mScreenScale, ( int ) ((( float ) mViewHeight) / 40 * 28),
                mProgressWidth + 17 * mScreenScale,
                ( int ) ((( float ) mViewHeight) / 40 * 38));
        mPaint.setColor(Color.parseColor("#d9d9d9"));
        canvas.drawRoundRect(mTopDestRect, 100, 100, mPaint);

        //绘制进度条的头部
        canvas.drawBitmap(mProgressBitmap, 18 * mScreenScale + mStepLength * mProgress,
                ( int ) ((( float ) mViewHeight) / 40 * 26), mPaint);

        mPaint.setColor(Color.parseColor("#7ec059"));
        mPaint.setTextSize(12 * mScreenScale);
        //绘制进度值背景
        canvas.drawBitmap(mProgressTipsBitmap, 18 * mScreenScale + mStepLength * mProgress - 10 * mScreenScale,
                0, mPaint);

        //绘制进度值
        canvas.drawText(mProgress + "%", 18 * mScreenScale + mStepLength * mProgress, 14 * mScreenScale, mPaint);

        //绘制走过的进度条的背景
        RectF mRect = new RectF(18 * mScreenScale, ( int ) ((( float ) mViewHeight) / 40 * 28), 18 * mScreenScale + mStepLength * mProgress + 10,
                ( int ) ((( float ) mViewHeight) / 40 * 38));
        canvas.drawRoundRect(mRect, 100, 100, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = ( int ) (280 * mScreenScale);
        int desiredHeight = ( int ) (40 * mScreenScale);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //Measure Width
        if ( widthMode == MeasureSpec.EXACTLY ) {
            //Must be this size
            mViewWidth = widthSize;
        } else if ( widthMode == MeasureSpec.AT_MOST ) {
            //Can't be bigger than...
            mViewWidth = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            mViewWidth = desiredWidth;
        }

        //Measure Height
        if ( heightMode == MeasureSpec.EXACTLY ) {
            //Must be this size
            mViewHeight = heightSize;
        } else if ( heightMode == MeasureSpec.AT_MOST ) {
            //Can't be bigger than...
            mViewHeight = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            mViewHeight = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(mViewWidth, mViewHeight);
        mProgressWidth = ( int ) (( float ) mViewWidth / 280 * 244);
//        mStepLength = (244 * mScreenScale - 17 * mScreenScale) / 100;
        mStepLength = mProgressWidth / 100;

        Log.e("w + h:", mViewWidth + "  " + mViewHeight + "   " + mStepLength + "  " + mProgressWidth);
    }

    //获取屏幕分辨率比例
    private float getScreenScale(Context context) {
        TextView tv = new TextView(context);
        tv.setTextSize(1);
        return tv.getTextSize();
    }
}
